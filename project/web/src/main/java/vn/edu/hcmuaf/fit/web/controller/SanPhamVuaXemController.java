package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "view", value = "/view")
public class SanPhamVuaXemController extends HttpServlet {
    private ProductServiece productService = new ProductServiece();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
            List<Product> products = productService.getViewedProductsByUserId(user.getId()); // Lấy sản phẩm đã xem
            request.setAttribute("products", products);
        }
        List<ProductType> data=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", data);

        request.getRequestDispatcher("/sanphamvuaxem.jsp").forward(request, response);
    }
}