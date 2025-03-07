package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.CommentServiece;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();

        List<ProductType> data=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", data);

        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }

        ListProduct listProduct = new ListProduct();
        List<Product> mostSaleProducts = listProduct.getProductsByQuantitySale();
        List<Product> mostViewedProducts = listProduct.getMostViewedProduct(3);
        List<Product> mostViewedProductsNones = listProduct.getMostViewedProductNone(3,6);
        List<Product> entertainmentProducts = listProduct.getTypeProducts(1,5);
        List<Product> workProducts = listProduct.getTypeProducts(3,5);
        List<Product> utilityProducts = listProduct.getTypeProducts(4,5);

        request.setAttribute("mostSaleProducts", mostSaleProducts);
        request.setAttribute("entertainmentProducts", entertainmentProducts);
        request.setAttribute("workProducts", workProducts);
        request.setAttribute("utilityProducts", utilityProducts);
        request.setAttribute("mostViewedProducts", mostViewedProducts);
        request.setAttribute("mostViewedProductsNones", mostViewedProductsNones);
        request.getRequestDispatcher("/Home.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
