package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;

@WebServlet("/addProduct")
public class AddProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("price"));
        String duration = request.getParameter("duration");
        String img = request.getParameter("img");
        String des = request.getParameter("des");
        String intro  = request.getParameter("intro");
        String manu = request.getParameter("manu");
        String support = request.getParameter("support");
        String banner = request.getParameter("banner");

        AdminService adminService = new AdminService();
        adminService.addProduct(name,type,price,duration,img,des,intro,manu,support,banner);
        response.sendRedirect("ProductManagement");

    }
}
