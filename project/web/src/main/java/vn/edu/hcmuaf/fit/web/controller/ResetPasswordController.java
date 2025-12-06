package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String action = (String) session.getAttribute("action");

        if (email == null) {
            response.sendRedirect("forgetpassword.jsp");
            return;
        }
        String newPassword = request.getParameter("new_password");
        String confirm_password = request.getParameter("confirm_password");

        // Kiểm tra mật khẩu nhập lại có khớp không
        if (newPassword == null || confirm_password == null || !newPassword.equals(confirm_password)) {
            session.setAttribute("error", "Mật khẩu xác nhận không trùng khớp.");
            response.sendRedirect(request.getHeader("referer")); // Quay lại trang trước đó
            return;
        }

        if ("resetPassword".equals(action)) {
//             // Trường hợp quên mật khẩu - đặt lại bằng OTP
            boolean isSuccess = resetPasswordService.setNewPassword(email, newPassword);
            if (isSuccess) {
                session.removeAttribute("email"); // Xóa email khỏi session sau khi đổi mật khẩu thành công
                session.removeAttribute("action");
                session.setAttribute("error", "Đổi mật khẩu thành công, vui lòng đăng nhập lại.");
                response.sendRedirect("/web/home");
            } else {
                session.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại.");
                response.sendRedirect("changePassword.jsp");
            }
            return;
        }else {
            // Trường hợp đổi mật khẩu khi đã đăng nhập
            String oldPassword = request.getParameter("old_password");
            if (oldPassword == null) {
                session.setAttribute("error", "Vui lòng nhập mật khẩu cũ.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }
            boolean isSuccess = resetPasswordService.resetPassword(email, oldPassword, newPassword);
            if (isSuccess) {
                session.setAttribute("error", "Đổi mật khẩu thành công");
                response.sendRedirect("/web/account");
            } else {
                session.setAttribute("error", "Mật khẩu cũ không đúng.");
                response.sendRedirect(request.getHeader("referer"));
            }
        }
    }
}