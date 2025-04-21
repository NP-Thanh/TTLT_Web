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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginService loginService;
    private XacThucOTPService xacThucOTPService;
    private static final int MAX_ATTEMPTS = 5;
    // Facebook API
    private static final String FACEBOOK_APP_ID = "608397805533371";
    private static final String FACEBOOK_APP_SECRET = "f245e640a95d0e7cbdb122f9e41deac9";
    private static final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/web/home"; // Địa chỉ callback

    // Google API
    private static final String GOOGLE_CLIENT_ID = "720220286630-t5bd3utvmrbm8omsfkjajh2prod7ecgl.apps.googleusercontent.com";
    private static final String GOOGLE_CLIENT_SECRET = "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"; // Thay bằng Client Secret của bạn
    private static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/web/home"; // Địa chỉ callback

    @Override
    public void init() {
        loginService = new LoginService();
        xacThucOTPService = new XacThucOTPService();
    }

    // Xử lý yêu cầu GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("facebook".equals(action)) {
            String facebookLoginUrl = "https://www.facebook.com/v10.0/dialog/oauth?client_id=" + FACEBOOK_APP_ID
                    + "&redirect_uri=" + FACEBOOK_REDIRECT_URI
                    + "&scope=email,public_profile";
            response.sendRedirect(facebookLoginUrl);
        } else if ("callback".equals(action)) {
            String code = request.getParameter("code");
            handleFacebookLogin(code, request, response);
        } else if ("google".equals(action)) {
            String googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + GOOGLE_CLIENT_ID +
                    "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                    "&response_type=code&scope=email profile";
            response.sendRedirect(googleLoginUrl);
        } else if ("google/callback".equals(action)) {
            String code = request.getParameter("code");
            handleGoogleLogin(code, request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    // Xử lý yêu cầu POST
    // LoginController.java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserServiece userServiece = new UserServiece();
        String ip = request.getRemoteAddr();


        // Kiểm tra nếu tài khoản bị khóa
        if (xacThucOTPService.isIpLocked(ip)) {
            request.setAttribute("errorMessage", "Tài khoản của bạn đã bị khóa. Vui lòng thử lại sau 5 phút.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return; // Dừng tại đây để không tiếp tục xác thực
        }

        // Kiểm tra thông tin người dùng
        if (loginService.validateUser(email, password)) {
            // Đăng nhập thành công
            xacThucOTPService.resetOtpAttempt(email); // Reset số lần đăng nhập sai
            HttpSession session = request.getSession(true);
            session.setAttribute("uid",loginService.getID(email));
            User user= userServiece.getUserById((Integer) session.getAttribute("uid"));
            boolean isSuperAdmin = user.getRole_id() == 1;
            boolean isAdmin = isSuperAdmin || user.getRole_id() >= 3;

            session.setAttribute("admin", isAdmin);   // ← để LoginFilter dùng
            session.setAttribute("isSuperAdmin", isSuperAdmin);   // ← để ProductManagement dùng
            session.setAttribute("email", email);
            response.sendRedirect("/web/home");
        } else {
            // Xử lý đăng nhập thất bại
            xacThucOTPService.insertOtpAttemptByIP(email, ip); // Cập nhật số lần đăng nhập sai // Cập nhật số lần đăng nhập sai

            // Kiểm tra số lần đăng nhập sai
            Optional<OtpAttempt> otpAttempt = xacThucOTPService.getOtpAttemptByIP(ip);
            if (otpAttempt.isPresent() && otpAttempt.get().getFailedAttempts() >= MAX_ATTEMPTS) {
                xacThucOTPService.lockOtpByIP(ip); // Khóa tài khoản trong DB
                request.setAttribute("errorMessage", "Tài khoản của bạn đã bị khóa. Vui lòng thử lại sau 5 phút.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Đăng Nhập Thất Bại. Vui lòng kiểm tra lại email và mật khẩu.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    private void handleFacebookLogin(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = getFacebookAccessToken(code);
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        User user = fbClient.fetchObject("me", User.class); // Lấy thông tin người dùng từ Facebook

        HttpSession session = request.getSession(true);
        session.setAttribute("email", user.getEmail());  // Lưu email vào session
        session.setAttribute("name", user.getName());

        response.sendRedirect("/web/home");
    }

    private String getFacebookAccessToken(String code) {
        String accessToken = ""; // Biến để lưu access token
        try {
            String tokenUrl = "https://graph.facebook.com/v10.0/oauth/access_token?client_id=" + FACEBOOK_APP_ID +
                    "&redirect_uri=" + FACEBOOK_REDIRECT_URI +
                    "&client_secret=" + FACEBOOK_APP_SECRET +
                    "&code=" + code;

            // Thực hiện HTTP GET request để lấy access token
            HttpURLConnection conn = (HttpURLConnection) new URL(tokenUrl).openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Phân tích JSON để lấy access token
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();

        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL", e);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception", e);
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new RuntimeException("Json Syntax Exception", e);
        }
        return accessToken;
    }

    private void handleGoogleLogin(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = getGoogleAccessToken(code);
        GoogleUser user = getGoogleUserInfo(accessToken);

        HttpSession session = request.getSession(true);
        session.setAttribute("email", user.getEmail());
        session.setAttribute("name", user.getName());

        response.sendRedirect("/web/home");
    }

    private String getGoogleAccessToken(String code) {
        String accessToken = "";
        try {
            String tokenUrl = "https://oauth2.googleapis.com/token?client_id=" + GOOGLE_CLIENT_ID +
                    "&client_secret=" + GOOGLE_CLIENT_SECRET +
                    "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                    "&code=" + code +
                    "&grant_type=authorization_code";

            HttpURLConnection conn = (HttpURLConnection) new URL(tokenUrl).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Phân tích dữ liệu JSON để lấy access token
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
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

            // Phân tích dữ liệu JSON và lưu vào GoogleUser
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            user.setEmail(jsonObject.get("email").getAsString());
            user.setName(jsonObject.get("name").getAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}