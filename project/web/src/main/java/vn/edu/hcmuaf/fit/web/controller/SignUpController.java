package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.servieces.SignUpService;
import vn.edu.hcmuaf.fit.web.servieces.XacThucOTPService;

import java.io.IOException;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {
    private SignUpService signUpService;
    private XacThucOTPService xacThucOtpService;

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDao();
        signUpService = new SignUpService(userDao);
        xacThucOtpService = new XacThucOTPService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        // Gọi SignUpService để đăng ký người dùng
        String resultMessage = signUpService.registerUser(
                request.getParameter("name"), email, password, request.getParameter("phone"), password2, request.getSession()
        );

        // Xử lý kết quả
        if (resultMessage.equals("Đăng ký thành công, vui lòng xác thực OTP!")) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);

            // Gửi OTP qua email
            String otpMessage = xacThucOtpService.otp(email, session);
            if (otpMessage.startsWith("Lỗi")) {
                request.setAttribute("notification", otpMessage);
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("xacThucOTP.jsp").forward(request, response);
        } else {
            request.setAttribute("notification", "Lỗi gửi OTP`");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
