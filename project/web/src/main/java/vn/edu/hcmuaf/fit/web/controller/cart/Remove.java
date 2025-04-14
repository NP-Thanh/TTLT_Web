package vn.edu.hcmuaf.fit.web.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.servieces.CartService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Remove", value = "/remove-cart")
public class Remove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartService cartService = new CartService();
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(idParam);
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("uid");

        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/web/login");
            return;
        }

        Cart cart = cartService.getCartByUserId(userId);
        if (cart != null) {
            cartService.removeProductFromCart(cart.getId(), id);

            // Sau khi cập nhật giỏ hàng
            List<CartProduct> cartItems = cartService.getCartProducts(cart.getId());
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("/cartFragment.jsp").include(request, response); // Sử dụng include để trả về phần giỏ hàng
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần thiết cho phương thức POST trong trường hợp này
    }
}