package vn.edu.hcmuaf.fit.web.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.servieces.CartService;
import java.io.IOException;

@WebServlet(name = "Remove", value = "/remove-cart")
public class Remove extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartService cartService = new CartService();
        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect("Cart");
            return;
        }

        int id = Integer.parseInt(idParam);
        HttpSession session = request.getSession(true);
        Integer userId = (Integer)session.getAttribute("uid");

        if (userId == null) {
            response.sendRedirect("Home.jsp");
            return;
        }

        Cart cart = cartService.getCartByUserId(userId);

        if (cart != null) {
            cartService.removeProductFromCart(cart.getId(), id);
        }

        String referer = request.getHeader("referer"); // Lấy URL trước đó
        if (referer != null) {
            response.sendRedirect(referer); // Quay lại trang trước
        } else {
            response.sendRedirect("ProductDetail?addCart=true"); // Phòng trường hợp không có URL trước đó
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
