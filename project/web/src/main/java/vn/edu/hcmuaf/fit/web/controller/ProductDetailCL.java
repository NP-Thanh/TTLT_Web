package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.*;
import vn.edu.hcmuaf.fit.web.servieces.CommentServiece;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetail", value = "/ProductDetail")
public class ProductDetailCL extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        CommentServiece commentServiece = new CommentServiece();
        ListProduct listProduct = new ListProduct();
        List<ProductType> types=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", types);

        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }
        String idParam = request.getParameter("id");
        if(session.getAttribute("uid")!=null){
            listProduct.viewProduct((int) session.getAttribute("uid"),Integer.parseInt(idParam));
        }

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                // Lấy thông tin những comment sản phẩm
                List<Comment> comments= commentServiece.getAllByPID(id);
                request.setAttribute("comments", comments);
                double rateAVG= commentServiece.getAverageRatingByPID(id);
                request.setAttribute("rateAVG", rateAVG);
                List<CommentByUser> commentsByUser= commentServiece.get2CommentByUser(id);
                request.setAttribute("commentsByUser", commentsByUser);

                // Lấy thông tin sản phẩm và chi tiết
                ProductWithDetail productWithDetail = productServiece.getProductWithDetailById(id);

                // Lấy những sản phẩm liên quan
                List<Product> relatedProducts= productServiece.getRelatedProducts(id);

                if (productWithDetail != null) {
                    request.setAttribute("product", productWithDetail.getProduct());
                    request.setAttribute("productDetail", productWithDetail.getProductDetail());
                    request.setAttribute("relatedProducts", relatedProducts);
                } else {
                    request.setAttribute("error", "Product not found!");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid product ID!");
            }
        } else {
            request.setAttribute("error", "Product ID is required!");
        }

        request.getRequestDispatcher("/product_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
