package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.model.Bank;
import vn.edu.hcmuaf.fit.web.model.OrderWithUser;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductManage;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;

import java.io.IOException;
import java.util.List;

@WebServlet("/ProductManagement")
public class ProductManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminService adminService = new AdminService();
        HttpSession session = request.getSession(false); // false để không tự tạo mới session
        if (session == null) {
            response.sendRedirect("/login");
            return;
        }

        int uid = (int) session.getAttribute("uid");
        boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");

        // Chỉ cho phép thêm sp loại sp mà admin đc phân
        List<String> allowedType = null;
        if (!isSuperAdmin) {
            allowedType = adminService.getProductTypeByUserId(uid);
            request.setAttribute("allowedType", allowedType);
        }

        List<ProductManage> products = adminService.getProductListByRole(uid, isSuperAdmin);

        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Xem quản lý sản phẩm", uid, "list products", "list products");
        request.setAttribute("products", products);
        request.getRequestDispatcher("/productManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer productId = request.getParameter("productID") != null && !request.getParameter("productID").isEmpty()
                ? Integer.parseInt(request.getParameter("productID"))
                : null;
        String productName = request.getParameter("productName");
        String status = request.getParameter("status");

        AdminService adminService = new AdminService();
        List<ProductManage> filteredProducts = adminService.filterProducts(productId, productName, status);

        request.setAttribute("products", filteredProducts);
        request.getRequestDispatcher("/productManagement.jsp").forward(request, response);
    }
}