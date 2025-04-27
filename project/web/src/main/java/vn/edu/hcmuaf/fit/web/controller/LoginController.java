package vn.edu.hcmuaf.fit.web.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.model.GoogleUser;
import vn.edu.hcmuaf.fit.web.model.OtpAttempt;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.LoginService;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;
import vn.edu.hcmuaf.fit.web.servieces.XacThucOTPService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginService loginService;
    private XacThucOTPService xacThucOTPService;
    private static final int MAX_ATTEMPTS = 5;

    private static final String FACEBOOK_APP_ID = "608397805533371";
    private static final String FACEBOOK_APP_SECRET = "f245e640a95d0e7cbdb122f9e41deac9";
    private static final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/web/login?action=facebook";

    private static final String GOOGLE_CLIENT_ID = "720220286630-t5bd3utvmrbm8omsfkjajh2prod7ecgl.apps.googleusercontent.com";
    private static final String GOOGLE_CLIENT_SECRET = "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";
    private static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/web/login?action=google";

    @Override
    public void init() {
        loginService = new LoginService();
        xacThucOTPService = new XacThucOTPService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String code = request.getParameter("code");

        if ("facebook".equals(action)) {
            if (code == null || code.isEmpty()) {
                String facebookLoginUrl = "https://www.facebook.com/v10.0/dialog/oauth?client_id=" + FACEBOOK_APP_ID
                        + "&redirect_uri=" + FACEBOOK_REDIRECT_URI
                        + "&scope=email,public_profile";
                response.sendRedirect(facebookLoginUrl);
                return;
            }
            handleFacebookLogin(code, request, response);
            return;
        }

        if ("google".equals(action)) {
            if (code == null || code.isEmpty()) {
                String googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id="
                        + GOOGLE_CLIENT_ID
                        + "&redirect_uri=" + GOOGLE_REDIRECT_URI
                        + "&response_type=code&scope=email profile openid";
                response.sendRedirect(googleLoginUrl);
                return;
            }
            handleGoogleLogin(code, request, response);
            return;
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserServiece userServiece = new UserServiece();
        String ip = request.getRemoteAddr();

        if (xacThucOTPService.isIpLocked(ip)) {
            request.setAttribute("errorMessage", "Tài khoản của bạn đã bị khóa. Vui lòng thử lại sau 5 phút.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (loginService.validateUser(email, password)) {
            xacThucOTPService.resetOtpAttempt(email);
            HttpSession session = request.getSession(true);
            session.setAttribute("uid", loginService.getID(email));
            User user = userServiece.getUserById((Integer) session.getAttribute("uid"));
            boolean isSuperAdmin = user.getRole_id() == 1;
            boolean isAdmin = isSuperAdmin || user.getRole_id() >= 3;

            session.setAttribute("admin", isAdmin);
            session.setAttribute("isSuperAdmin", isSuperAdmin);
            session.setAttribute("email", email);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            xacThucOTPService.insertOtpAttemptByIP(email, ip);
            Optional<OtpAttempt> otpAttempt = xacThucOTPService.getOtpAttemptByIP(ip);
            if (otpAttempt.isPresent() && otpAttempt.get().getFailedAttempts() >= MAX_ATTEMPTS) {
                xacThucOTPService.lockOtpByIP(ip);
                request.setAttribute("errorMessage", "Tài khoản của bạn đã bị khóa. Vui lòng thử lại sau 5 phút.");
            } else {
                request.setAttribute("errorMessage", "Đăng Nhập Thất Bại. Vui lòng kiểm tra lại email và mật khẩu.");
            }
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleFacebookLogin(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = getFacebookAccessToken(code);
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        com.restfb.types.User fbUser = fbClient.fetchObject("me?fields=id,name,email", com.restfb.types.User.class);

        UserServiece userService = new UserServiece();
        User user = new User();
        user.setEmail(fbUser.getEmail());
        user.setName(fbUser.getName());
        user.setPassword("");
        user.setPhone("");
        user.setStatus("Hoạt động");
        user.setRole_id(2);

        userService.createIfNotExists(user);
        user = userService.findUserByEmail(user.getEmail());

        HttpSession session = request.getSession(true);
        session.setAttribute("email", fbUser.getEmail());
        session.setAttribute("name", fbUser.getName());
        session.setAttribute("uid", user.getId());

        boolean isSuperAdmin = user.getRole_id() == 1;
        boolean isAdmin = isSuperAdmin || user.getRole_id() >= 3;
        session.setAttribute("admin", isAdmin);
        session.setAttribute("isSuperAdmin", isSuperAdmin);

        response.sendRedirect(request.getContextPath() + "/home");
    }

    private void handleGoogleLogin(String code, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String accessToken = getGoogleAccessToken(code);
        GoogleUser googleUser = getGoogleUserInfo(accessToken);

        if (googleUser.getEmail() == null || googleUser.getEmail().isEmpty()) {
            request.setAttribute("errorMessage", "Không thể lấy email từ Google. Vui lòng thử lại.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        UserServiece userService = new UserServiece();
        User user = userService.findUserByEmail(googleUser.getEmail());

        if (user == null) {
            user = new User();
            user.setEmail(googleUser.getEmail());
            user.setName(googleUser.getName());
            user.setPassword("");
            user.setPhone("");
            user.setStatus("Hoạt động");
            user.setRole_id(2);
            userService.createIfNotExists(user);
            user = userService.findUserByEmail(googleUser.getEmail());
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("email", googleUser.getEmail());
        session.setAttribute("name", googleUser.getName());
        session.setAttribute("uid", user.getId());

        boolean isSuperAdmin = user.getRole_id() == 1;
        boolean isAdmin = isSuperAdmin || user.getRole_id() >= 3;
        session.setAttribute("admin", isAdmin);
        session.setAttribute("isSuperAdmin", isSuperAdmin);

        response.sendRedirect(request.getContextPath() + "/home");
    }

    private String getFacebookAccessToken(String code) {
        try {
            String tokenUrl = "https://graph.facebook.com/v10.0/oauth/access_token?client_id=" + FACEBOOK_APP_ID +
                    "&redirect_uri=" + FACEBOOK_REDIRECT_URI +
                    "&client_secret=" + FACEBOOK_APP_SECRET +
                    "&code=" + code;

            HttpURLConnection conn = (HttpURLConnection) new URL(tokenUrl).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            return jsonObject.get("access_token").getAsString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy access token Facebook", e);
        }
    }

    private String getGoogleAccessToken(String code) {
        try {
            String tokenUrl = "https://oauth2.googleapis.com/token";
            HttpURLConnection conn = (HttpURLConnection) new URL(tokenUrl).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String postData = "client_id=" + GOOGLE_CLIENT_ID +
                    "&client_secret=" + GOOGLE_CLIENT_SECRET +
                    "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                    "&code=" + code +
                    "&grant_type=authorization_code";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            return jsonObject.get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private GoogleUser getGoogleUserInfo(String accessToken) {
        GoogleUser user = new GoogleUser();
        try {
            String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

            HttpURLConnection conn = (HttpURLConnection) new URL(userInfoUrl).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            System.out.println("Google user info: " + jsonObject);

            if (jsonObject.has("email")) {
                user.setEmail(jsonObject.get("email").getAsString());
            }
            if (jsonObject.has("name")) {
                user.setName(jsonObject.get("name").getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}