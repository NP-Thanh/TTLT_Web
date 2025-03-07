package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.web.dao.CommentDao;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.*;
import vn.edu.hcmuaf.fit.web.servieces.CommentServiece;
import vn.edu.hcmuaf.fit.web.servieces.DiscountService;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        List<ProductType> types=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", types);

        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }

        CommentServiece commentServiece = new CommentServiece();
        OrderServiece orderServiece = new OrderServiece();
        DiscountService discountServiece = new DiscountService();

        String idParam = request.getParameter("id");
        String productIdParam = request.getParameter("pid");
        if (idParam != null && !idParam.isEmpty() && productIdParam != null && !productIdParam.isEmpty()) {
            try {
                int oid = Integer.parseInt(idParam);
                int pid = Integer.parseInt(productIdParam);
                // Lấy thông tin sản phẩm liên quan
                List<Product> relatedProducts= productServiece.getRelatedProducts(pid);
                request.setAttribute("relatedProducts", relatedProducts);

                // Lấy thông tin list order detail
                OrderWithUser orderWithUser = orderServiece.getOrderWithUserByOID(oid);
                if (orderWithUser != null) {
                    request.setAttribute("orderWithUser", orderWithUser);

                    // Lấy thông tin order
                    Order order = orderWithUser.getOrder();
                    request.setAttribute("order", order);

                    // Lấy thông tin discount
                    if (order.getDiscount_id()>0){
                        Discount discount = discountServiece.getDiscountById(order.getDiscount_id());
                        request.setAttribute("discount", discount);
                    }

                    // Lấy thông tin user
                    User userOrder= orderWithUser.getUser();
                    request.setAttribute("userOrder", userOrder);

                    // Lấy cụ thể order detail và product
                    Product product = productServiece.getProductById(pid);

                    // Lấy thông tin comment của product theo mã đơn hàng
                    Comment comment = commentServiece.getCommentByOrder(order.getId(), product.getId());
                    if (comment != null) {
                        request.setAttribute("comment", comment);
                    }

                    if (orderServiece.checkProductInOrder(product, orderWithUser)) {
                        request.setAttribute("product", product);

                        OrderDetail orderDetail = orderServiece.getOrderDetail(orderWithUser, pid);
                        request.setAttribute("orderDetail", orderDetail);
                    } else {
                        request.setAttribute("error", "Product not in order!");
                    }
                } else {
                    request.setAttribute("error", "Order not found!");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid order ID!");
            }
        } else {
            request.setAttribute("error", "Order ID is required!");
        }

        request.getRequestDispatcher("/order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        // Chuyển chuỗi JSON thành JSONObject
        JSONObject jsonRequest = new JSONObject(sb.toString());

        // Lấy các giá trị từ JSON
        int orderId = jsonRequest.getInt("order_id");
        int productId = jsonRequest.getInt("product_id");
        int userId = jsonRequest.getInt("user_id");
        int numRate = jsonRequest.getInt("num_rate");
        String comment = jsonRequest.getString("comment");

        // Xử lý dữ liệu (lưu vào database, gửi phản hồi...)
        CommentServiece commentServiece= new CommentServiece();
        boolean success = commentServiece.addComment(userId, productId, orderId, numRate, comment);

        // Trả về kết quả dưới dạng JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": " + success + "}");
        out.flush();
    }
}
