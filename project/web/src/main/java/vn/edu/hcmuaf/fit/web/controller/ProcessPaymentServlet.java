package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.CartService;
import vn.edu.hcmuaf.fit.web.servieces.DiscountService;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProcessPaymentServlet", value = "/processPayment")
public class ProcessPaymentServlet extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserServiece userServiece = new UserServiece();
        DiscountService discountServiece = new DiscountService();
        CartService cartServiece = new CartService();

        // Kiểm tra nếu người dùng đã đăng nhập
        Integer userId = (Integer) session.getAttribute("uid");
        if (userId == null) {
            response.sendRedirect("login"); // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return;
        }

        // Lấy thông tin giỏ hàng từ  database
        Cart cart = cartServiece.getCartByUserId(userId);
        if (cart == null) {
            response.getWriter().write("Không tìm thấy giỏ hàng.");
            return;
        }

        List<CartProduct> cartItems = cartServiece.getCartProducts(cart.getId());

        // Kiểm tra giỏ hàng có sản phẩm không
        if (cartItems == null || cartItems.isEmpty()) {
            response.getWriter().write("Giỏ hàng không có sản phẩm.");
            return;
        }

        // Lấy discount_id từ request parameter
        String did = request.getParameter("did");
        Integer discountId = null;
        if (did != null && !did.isEmpty()) {
            discountId = Integer.parseInt(did);
        }

        // Lấy tổng tiền từ giỏ hàng
        double totalAmount = cartServiece.getTotalPrice(cart.getId());

        // Lấy user từ database
        User user = userServiece.getUserById(userId);

        // Insert vào bảng orders
        OrderServiece ordersServiece = new OrderServiece();
        int orderId = ordersServiece.insertOrder(userId, discountId, totalAmount);

        // Insert vào bảng order_detail
        for (CartProduct item : cartItems) {
            ordersServiece.insertOrderDetail(orderId, item.getProduct_id(), item.getQuantity(), item.getPrice());
        }
        cartServiece.deleteCart(cart.getId());
        // Trả về thông báo hoặc chuyển hướng đến trang xác nhận thanh toán
        response.sendRedirect("payment?oid=" + orderId + "&bid=1");
    }

}
