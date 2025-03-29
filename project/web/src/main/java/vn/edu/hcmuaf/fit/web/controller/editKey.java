package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;

@WebServlet("/editKey")
public class editKey extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("kid"));
        String key = request.getParameter("kName");
        String productName = request.getParameter("pName");
        String productType = request.getParameter("pType");
        String image = request.getParameter("pImg");

        AdminService adminService = new AdminService();
        adminService.editKey(id, key, productName, productType, image);
        response.sendRedirect("KeyManagement");
    }
}
