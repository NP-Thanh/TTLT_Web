package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;

@WebServlet("/editProduct")
public class editProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("pid"));
        String name = request.getParameter("pName");
        String type = request.getParameter("pType");
        double price = Double.parseDouble(request.getParameter("pPrice"));
        String duration = request.getParameter("pDuration");
        String image = request.getParameter("pImg");
        String description = request.getParameter("pDescription");
        String introduction = request.getParameter("pIntroduction");
        String manufacturer = request.getParameter("pManufacturer");
        String support = request.getParameter("pSupport");

        AdminService adminService = new AdminService();
        adminService.editProduct(id,name,type,price,duration,image,description,introduction,manufacturer,support);
        response.sendRedirect("ProductManagement");

    }
}
