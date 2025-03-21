package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.OtpAttemptDao;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.OtpAttempt;
import vn.edu.hcmuaf.fit.web.servieces.SignUpService;
import vn.edu.hcmuaf.fit.web.servieces.XacThucOTPService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/xacThucOTP")
public class XacThucOTPController extends HttpServlet {
    private static final long LOCK_TIME = 5 * 60 * 1000;  //Khóa 5 phút
    private XacThucOTPService xacThucOtpService;
    private SignUpService signUpService;
    private OtpAttemptDao otpAttemptDao;

    @Override
    public void init() throws ServletException {
        super.init();
        UserDao userDao = new UserDao();
        signUpService = new SignUpService(userDao);
        xacThucOtpService = new XacThucOTPService();
        this.otpAttemptDao = new OtpAttemptDao(JDBIConnector.getJdbi());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/xacThucOTP.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String action = (String) session.getAttribute("action");

        System.out.println("Session ID: " + session.getId() + " - Email: " + session.getAttribute("email"));

        if (email == null || action == null) {
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

        boolean isValid = xacThucOtpService.verifyOtp(session, otp, email);

        if (!isValid) {
            session.setAttribute("notification", "OTP không đúng hoặc đã hết hạn!");
            response.sendRedirect("xacThucOTP.jsp");
            return;
        }

        if ("signup".equals(action)) {
            if (signUpService.confirmOtpAndSaveUser(email,session)) {
                response.sendRedirect("login.jsp");
            } else {
                session.setAttribute("notification", "Lỗi khi lưu thông tin đăng ký.");
                response.sendRedirect("xacThucOTP.jsp");
            }
        } else if ("resetPassword".equals(action)) {
            session.removeAttribute("otpCode"); // Xóa OTP sau khi xác thực thành công
            session.removeAttribute("otpExpiry");
            response.sendRedirect("changePassword.jsp");
        }
    }
}
