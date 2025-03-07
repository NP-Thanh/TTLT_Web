package vn.edu.hcmuaf.fit.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.demo.service.LoginService;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginService loginService;

    @Override
    public void init() {
        loginService = new LoginService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (loginService.validateUser(email, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("uid",loginService.getID(email));
            response.sendRedirect("home.jsp");
        } else {
            response.getWriter().println("<h2>Đăng Nhập Thất Bại. Vui lòng kiểm tra lại email và mật khẩu.</h2>");
            response.sendRedirect("login.jsp");
        }
    }
}