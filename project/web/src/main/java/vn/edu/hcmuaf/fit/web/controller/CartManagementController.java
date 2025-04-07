package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;
import java.util.List;

@WebServlet("/CartManagement")

public class CartManagementController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AdminService adminService = new AdminService();
        List<CartProduct> carts = adminService.getAllListCartDetails();
        request.setAttribute("cart", carts);
        request.getRequestDispatcher("/cartManagement.jsp").forward(request, response);

    }
}
