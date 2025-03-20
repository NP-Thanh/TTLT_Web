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
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.LoginService;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginService loginService;

    private static final String FACEBOOK_APP_ID = "608397805533371";
    private static final String FACEBOOK_APP_SECRET = "f245e640a95d0e7cbdb122f9e41deac9";
    private static final String REDIRECT_URI =  "http://localhost:8080/web/login/facebook/callback"; // Địa chỉ callback

    @Override
    public void init() {
        loginService = new LoginService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("facebook".equals(action)) {
            String facebookLoginUrl = "https://www.facebook.com/v10.0/dialog/oauth?client_id=" + FACEBOOK_APP_ID
                    + "&redirect_uri=" + REDIRECT_URI
                    + "&scope=email,public_profile";
            response.sendRedirect(facebookLoginUrl);
        } else if ("callback".equals(action)) {
            String code = request.getParameter("code");
            handleFacebookLogin(code, request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserServiece userServiece = new UserServiece();

        if (loginService.validateUser(email, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("uid", loginService.getID(email));
            User user = userServiece.getUserById((Integer) session.getAttribute("uid"));
            boolean admin = user.getRole_id() == 1;
            session.setAttribute("admin", admin);
            session.setAttribute("email", email);
            response.sendRedirect("/web/home");
        } else {
            response.getWriter().println("<h2>Đăng Nhập Thất Bại. Vui lòng kiểm tra lại email và mật khẩu.</h2>");
            response.sendRedirect("/web/login");
        }
    }

    private void handleFacebookLogin(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy Access Token từ Facebook với mã code
        String accessToken = getAccessToken(code);
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        User user = fbClient.fetchObject("me", User.class); // Lấy thông tin người dùng từ Facebook

        // Xử lý lưu thông tin người dùng vào session
        HttpSession session = request.getSession(true);
        session.setAttribute("email", user.getEmail());  // Lưu email vào session
        session.setAttribute("name", user.getName());

        // Chuyển hướng đến trang chính sau khi đăng nhập
        response.sendRedirect("/web/home");
    }

    private String getAccessToken(String code) {
        String accessToken = ""; // Biến để lưu access token
        try {
            String tokenUrl = "https://graph.facebook.com/v10.0/oauth/access_token?client_id=" + FACEBOOK_APP_ID +
                    "&redirect_uri=" + REDIRECT_URI +
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
}