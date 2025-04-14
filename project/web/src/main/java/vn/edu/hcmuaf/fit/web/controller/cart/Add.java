package vn.edu.hcmuaf.fit.web.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.servieces.CartService;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        CartService cartService = new CartService();

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(idParam);
        Product pid = productServiece.getProductById(id);
        if (pid == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("uid");

        if (userId == null) {
            response.sendRedirect("/web/login");
            return;
        }

        Cart cart = cartService.getCartByUserId(userId);
        int cartId;
        if (cart == null) {
            cartId = cartService.createCart(userId);
        } else {
            cartId = cart.getId();
        }

        List<CartProduct> items = cartService.getCartProducts(cartId);
        CartProduct existing = items.stream()
                .filter(p -> p.getProduct_id() == id)
                .findFirst().orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);
            existing.setPrice(pid.getPrice());
            cartService.updateProductQuantity(existing);
        } else {
            CartProduct newItem = new CartProduct();
            newItem.setCartId(cartId);
            newItem.setProduct_id(pid.getId());
            newItem.setQuantity(1);
            newItem.setPrice(pid.getPrice());
            cartService.addProductToCart(newItem);
        }

        request.getRequestDispatcher("ProductDetail?id=" + id).forward(request, response);
        // Sau khi cập nhật giỏ hàng
        request.setAttribute("cartItems", cartService.getCartProducts(cartId));
        request.getRequestDispatcher("/cartFragment.jsp").include(request, response);
    }
}