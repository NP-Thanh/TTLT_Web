package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.dao.UserDao;

import java.io.IOException;

@WebServlet("/update-user")
public class UpdateUserController extends HttpServlet {
    private final UserDao userDAO = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {int role_id = Integer.parseInt(req.getParameter("role_id"));
        int user_id = Integer.parseInt(req.getParameter("user_id"));

        userDAO.setRole(role_id, user_id);

        resp.sendRedirect(req.getContextPath() + "/user");
    }
}
