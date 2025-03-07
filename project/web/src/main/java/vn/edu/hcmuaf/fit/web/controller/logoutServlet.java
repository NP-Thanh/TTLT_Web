package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/logout")
public class logoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hủy session khi người dùng đăng xuất

        HttpSession session = request.getSession(true);
        if (session != null) {
            session.invalidate(); // Hủy session
        }
        // Chuyển hướng về trang chủ hoặc trang đăng nhập
        response.sendRedirect("home"); // Hoặc "login"
    }
}
