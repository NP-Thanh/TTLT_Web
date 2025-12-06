package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.UpdateProfileService;

import java.io.IOException;

@WebServlet("/updateProfile")
public class UpdateProfileController extends HttpServlet {
    private UpdateProfileService updateProfileService;

    @Override
    public void init() throws ServletException {
        updateProfileService = new UpdateProfileService(); // Khởi tạo UpdateProfileService
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("full_name");
        String phone = request.getParameter("phone_number");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();

        System.out.println("Name: " + name + ", Phone: " + phone + ", Email: " + email); // Log thông tin


        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);

        if (updateProfileService.updateUserProfile(user)) {
            session.setAttribute("error", "Cập nhật thông tin thành công.");
            response.sendRedirect("account"); // Chuyển hướng đến trang tài khoản
        } else {
            request.setAttribute("errorMessage", "Cập nhật thông tin thất bại.");
            request.getRequestDispatcher("account").forward(request, response);
        }
    }
}