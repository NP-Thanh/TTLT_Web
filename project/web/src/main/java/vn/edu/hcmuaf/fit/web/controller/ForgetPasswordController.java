package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.servieces.ForgetPasswordService;

import java.io.IOException;

@WebServlet("/forgetPassword")
public class ForgetPasswordController extends HttpServlet {
    private final ForgetPasswordService forgetPasswordService = new ForgetPasswordService();

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("forgetpassword.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email").trim();

            HttpSession session = request.getSession();
            String resultMessage = forgetPasswordService.sendOTP(email, session);

            if (resultMessage.startsWith("Lỗi")) {
                request.setAttribute("resultMessage", resultMessage);
                request.getRequestDispatcher("forgetpassword.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("xacThucOTP.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}