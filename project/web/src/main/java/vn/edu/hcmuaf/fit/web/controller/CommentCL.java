package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.*;
import vn.edu.hcmuaf.fit.web.servieces.CommentServiece;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Comment", value = "/Comment")
public class CommentCL extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        CommentServiece commentServiece = new CommentServiece();

        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }

        List<ProductType> data=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", data);

        String idParam = request.getParameter("id");
        String ratingFilter = request.getParameter("ratingFilter");
        String sortOrder = request.getParameter("sortOrder");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                // Lấy thông tin sản phẩm
                ProductWithDetail productWithDetail = productServiece.getProductWithDetailById(id);
                if (productWithDetail != null) {
                    request.setAttribute("product", productWithDetail.getProduct());
                    request.setAttribute("productDetail", productWithDetail.getProductDetail());
                    // Lấy thông tin những comment sản phẩm
                    List<Comment> comments= commentServiece.getAllByPID(id);
                    request.setAttribute("comments", comments);
                    List<CommentByUser> commentByUsers= commentServiece.getFilteredComments(id, ratingFilter, sortOrder);
                    request.setAttribute("commentByUsers", commentByUsers);
                    request.setAttribute("ratingFilter", ratingFilter);
                    request.setAttribute("sortOrder", sortOrder);
                } else {
                    request.setAttribute("error", "Product not found!");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid product ID!");
            }
        } else {
            request.setAttribute("error", "Product ID is required!");
        }
        request.getRequestDispatcher("/comment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
