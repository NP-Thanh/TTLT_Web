package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Product;
//import org.jdbi.v3.core.Jdbi;
//import org.jdbi.v3.core.statement.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();

        List<ProductType> data=productServiece.getAllProductTypes();
        req.setAttribute("productTypes", data);

        UserDao userDao = new UserDao();
        HttpSession session = req.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            req.setAttribute("user", user);
        }

        String search = req.getParameter("search");
        String category = req.getParameter("category"); // Lấy tham số "category" từ URL
        String startPrice = req.getParameter("startPrice"); // Giá bắt đầu
        String endPrice = req.getParameter("endPrice"); // Giá kết thúc
        String sortByCreateAt = req.getParameter("sort");
        String sortByPriceASC = req.getParameter("sortasc");
        String sortByPriceDESC = req.getParameter("sortdesc");
        String sortBySale = req.getParameter("sortsale");
        ListProduct listProduct = new ListProduct();

        List<Product> products = listProduct.getAllProduct();



//         Tìm kiếm sản phẩm theo các tham số
        if (search != null && !search.isEmpty()) {
            products = listProduct.getSearchProducts(search);
        }if(category != null && !category.isEmpty()) {
            products = listProduct.getAllProductWithType(category);
        }if(startPrice != null && !startPrice.isEmpty() && endPrice != null && !endPrice.isEmpty()) {
            double startPriceDouble = Double.parseDouble(startPrice);
            double endPriceDouble = Double.parseDouble(endPrice);
            products = listProduct.getProductsByPrice(startPriceDouble,endPriceDouble);
        }if(sortByCreateAt != null && !sortByCreateAt.isEmpty()) {
            products = listProduct.getProductsByCreateAt();
        }
        if(sortByPriceASC != null && !sortByPriceASC.isEmpty()) {
            products = listProduct.getProductsByPriceASC();
        }
        if(sortByPriceDESC != null && !sortByPriceDESC.isEmpty()) {
            products = listProduct.getProductsByPriceDESC();
        }
        if(sortBySale != null && !sortBySale.isEmpty()) {
            products = listProduct.getProductsByQuantitySaleNoLimit();
        }
//        } else if (startPrice != null && !startPrice.isEmpty() && endPrice != null && !endPrice.isEmpty()) {
//            double start = Double.parseDouble(startPrice);
//            double end = Double.parseDouble(endPrice);
//            if (category != null && !category.isEmpty()) {
////                products = getProductsByCategoryAndPrice(category, start, end);
//            } else {
////                products = getProductsByPrice(start, end);
//            }
//        } else if (category != null && !category.isEmpty()) {
//            products = getProductsByCategory(category);
//        } else {
//            products = getAllProducts(); // Nếu không có tham số tìm kiếm, lấy tất cả sản phẩm
//        }
//
//        // Kiểm tra nếu không tìm thấy sản phẩm nào
//        if (products == null || products.isEmpty()) {
//            req.setAttribute("message", "Không tìm thấy sản phẩm nào.");
//        }

        req.setAttribute("products", products);
        req.getRequestDispatcher("/list-product.jsp").forward(req, resp);
    }

    // Tìm kiếm sản phẩm theo tên


}
