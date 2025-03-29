package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;

@WebServlet("/deleteKey")
public class deleteKey extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int kid = Integer.parseInt(request.getParameter("kid"));
        AdminService adminService = new AdminService();
        adminService.deleteKeyManage(kid);
        response.sendRedirect("KeyManagement");
    }
}
