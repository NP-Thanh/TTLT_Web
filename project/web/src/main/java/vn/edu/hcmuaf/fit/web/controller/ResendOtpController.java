package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.servieces.XacThucOTPService;

import java.io.IOException;

@WebServlet("/resendOtp")
public class ResendOtpController extends HttpServlet {
    private XacThucOTPService xacThucOtpService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.xacThucOtpService = new XacThucOTPService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            session.setAttribute("notification", "Email không hợp lệ. Vui lòng kiểm tra lại.");
            response.sendRedirect("xacThucOTP.jsp");
            return;
        }

        String message = xacThucOtpService.resendOtp(email, session);
        session.setAttribute("notification", message);
        response.sendRedirect("xacThucOTP.jsp");
    }
}
