package vn.edu.hcmuaf.fit.web.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.servieces.CartService;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Sub", value = "/sub-cart")
public class Sub extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        CartService cartService = new CartService();

        String idParam = request.getParameter("id");
        int id = Integer.parseInt(idParam);
        Product pid = productServiece.getProductById(id);
        // Nếu sản phẩm không tồn tại → redirect sớm
        if (pid == null) {
            response.sendRedirect("ProductDetail?addCart=false");
            return; // DỪNG xử lý
        }

        HttpSession session = request.getSession();
        Integer userId = (Integer)session.getAttribute("uid");

        if (userId == null) {
            response.sendRedirect("Home.jsp");
            return;
        }

//        Kiểm tra user đã có cart chưa
        Cart cart = cartService.getCartByUserId(userId);
        int cartId;
        if (cart == null) {
            cartId = cartService.createCart(userId);
        } else {
            cartId = cart.getId();
        }

//        Kiểm tra sản phẩm đã tồn tại trong giỏ chưa
        List<CartProduct> cartItems = cartService.getCartProducts(cartId);
        CartProduct existing = cartItems.stream()
                .filter(p -> p.getProduct_id() == id)
                .findFirst().orElse(null);

        if (existing != null) {
            if (existing.getQuantity() > 1) {
                existing.setQuantity(existing.getQuantity() - 1);
                existing.setPrice(pid.getPrice());
                cartService.updateProductQuantity(existing);
            }
        }else {
            CartProduct newItem = new CartProduct();
            newItem.setCartId(cartId);
            newItem.setProduct_id(pid.getId());
            newItem.setQuantity(1);
            newItem.setPrice(pid.getPrice());
            cartService.addProductToCart(newItem);
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
