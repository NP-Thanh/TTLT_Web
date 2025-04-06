package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.CartDao;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;

import java.util.List;

public class CartService {
    private CartDao cartDao;

    public CartService() {
        this.cartDao = new CartDao();
    }

    public int createCart(int userId) {
        return cartDao.createCart(userId); // Tạo giỏ hàng cho người dùng
    }

    public boolean addProductToCart(CartProduct cartProduct) {
        return cartDao.addCartDetail(cartProduct); // Thêm sản phẩm vào giỏ hàng
    }

    public boolean updateProductQuantity(CartProduct cartProduct) {
        return cartDao.updateCartDetail(cartProduct); // Cập nhật số lượng sản phẩm
    }

    public boolean removeProductFromCart(int cartId, int productId) {
        return cartDao.removeCartDetail(cartId, productId); // Xóa sản phẩm khỏi giỏ hàng
    }

    public List<CartProduct> getCartProducts(int cartId) {
        return cartDao.getCartDetails(cartId); // Lấy thông tin các sản phẩm trong giỏ hàng
    }

    public Cart getCartByUserId(int userId) {
        return cartDao.getCartByUserId(userId); // Lấy giỏ hàng của người dùng theo userId
    }

    public boolean deleteCart(int cartId) {
        return cartDao.deleteCart(cartId); // Xóa giỏ hàng
    }

    public double getTotalPrice(int cartId) {
        List<CartProduct> products = getCartProducts(cartId);
        return products.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum(); // Tính tổng giá
    }
}