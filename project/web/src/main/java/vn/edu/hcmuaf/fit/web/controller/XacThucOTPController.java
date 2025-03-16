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

@WebServlet("/xacThucOTP")
public class XacThucOTPController extends HttpServlet {
    private XacThucOTPService xacThucOtpService;
    private SignUpService signUpService;

    @Override
    public void init() throws ServletException {
        super.init();
        UserDao userDao = new UserDao();
        signUpService = new SignUpService(userDao);
        xacThucOtpService = new XacThucOTPService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/xacThucOTP.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            session.setAttribute("notification", "Phiên đăng nhập không hợp lệ, vui lòng đăng ký lại.");
            response.sendRedirect("signup.jsp");
            return;
        }

        String otp = request.getParameter("otp");
        if (otp == null || otp.trim().isEmpty()) {
            session.setAttribute("notification", "Vui lòng nhập mã OTP.");
            response.sendRedirect("xacThucOTP.jsp");
            return;
        }

        boolean isValid = xacThucOtpService.verifyOtp(session, otp);

        if (isValid) {
            if (signUpService.confirmOtpAndSaveUser(email, session)) {
                session.removeAttribute("otpCode");  // Xóa OTP khỏi session sau khi xác thực thành công
                session.removeAttribute("otpExpiry");
                response.sendRedirect("login.jsp");
            }else {
                session.setAttribute("notification", "Xác thực thất bại! Vui lòng thử lại.");
                response.sendRedirect("xacThucOTP.jsp");
            }
        } else {
            session.setAttribute("notification", "OTP không đúng hoặc đã hết hạn!");
            response.sendRedirect("xacThucOTP.jsp");
        }
    }
}
