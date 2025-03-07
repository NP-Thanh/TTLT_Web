package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;

@WebServlet("/addKey")
public class addProductKey extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        String key = request.getParameter("key");

        AdminService adminService = new AdminService();
        adminService.addProductKey(pid, key);
        response.sendRedirect("ProductManagement");

    }
}
