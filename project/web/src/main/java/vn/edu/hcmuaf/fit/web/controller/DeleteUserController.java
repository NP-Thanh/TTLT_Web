package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.dao.UserDao;

import java.io.IOException;

@WebServlet("/delete-user")
public class DeleteUserController extends HttpServlet {
    private final UserDao userDAO = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("user_id"));
        userDAO.deleteUser(userId, "Hạn chế");

        resp.sendRedirect(req.getContextPath() + "/user");
    }
}
