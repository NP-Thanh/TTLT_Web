package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.DiscountService;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymentProductServlet", value = "/paymentProduct")
public class PaymentProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserServiece userServiece = new UserServiece();
        DiscountService discountServiece = new DiscountService();
        ProductServiece productServiece = new ProductServiece();

        // Kiểm tra nếu người dùng đã đăng nhập
        Integer userId = (Integer) session.getAttribute("uid");
        if (userId == null) {
            response.sendRedirect("login"); // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return;
        }

        // Lấy user từ database
        User user = userServiece.getUserById(userId);

        // Lấy thông tin sản phẩm
        String productID = request.getParameter("pid");
        if (productID != null) {
            int pid = Integer.parseInt(productID);
            Product product = productServiece.getProductById(pid);
            // Insert vào bảng orders
            OrderServiece ordersServiece = new OrderServiece();
            int orderId = ordersServiece.insertOrder(userId, null, product.getPrice());
            // Insert vào bảng order_detail
            ordersServiece.insertOrderDetail(orderId, product.getId(), 1, product.getPrice());
            // Trả về thông báo hoặc chuyển hướng đến trang xác nhận thanh toán
            response.sendRedirect("payment?oid=" + orderId + "&bid=1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
