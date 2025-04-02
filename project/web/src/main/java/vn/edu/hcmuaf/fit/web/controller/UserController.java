package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.model.UserAdmin;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private final UserServiece userService = new UserServiece();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserAdmin> users = userService.getAllUserWithRole();
        HttpSession session = request.getSession(true);
        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Xem quản lý user", uid, "list users", "list users");
        request.setAttribute("users", users);
        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            User newUser = new User();
            newUser.setName(request.getParameter("name"));
            newUser.setEmail(request.getParameter("email"));
            newUser.setPhone(request.getParameter("phone"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setStatus("active");
            userService.addUser(newUser);
        } else if ("delete".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            userService.deleteUser(userId);
        }

        response.sendRedirect("user");
    }
}