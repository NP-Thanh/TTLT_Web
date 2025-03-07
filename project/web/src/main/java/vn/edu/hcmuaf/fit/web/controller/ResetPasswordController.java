package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.servieces.ResetPasswordService;

import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordController extends HttpServlet {
    private ResetPasswordService resetPasswordService;

    @Override
    public void init() throws ServletException {
        resetPasswordService = new ResetPasswordService(); // Khởi tạo ResetPasswordService
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");

        if (resetPasswordService.resetPassword(email, oldPassword, newPassword)) {
            response.sendRedirect("account"); // Thông báo thành công
        } else {
            request.setAttribute("errorMessage", "Mật khẩu cũ không đúng.");
            request.getRequestDispatcher("account").forward(request, response);
        }
    }
}