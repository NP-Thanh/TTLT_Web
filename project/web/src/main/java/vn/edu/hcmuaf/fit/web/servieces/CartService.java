package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.CartDao;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;

import java.sql.Connection;
import java.util.List;

public class CartService {
    private CartDao cartDao;

    public CartService(Connection connection) {
        this.cartDao = new CartDao(connection);
    }

    public boolean addProduct(CartProduct cartProduct) {
        return cartDao.add(cartProduct);
    }

    public boolean subtractProduct(int id) {
        return cartDao.sub(id);
    }

    public boolean updateProduct(CartProduct cartProduct) {
        return cartDao.update(cartProduct);
    }

    public boolean removeProduct(int id) {
        return cartDao.remove(id);
    }

    public List<CartProduct> getCartProducts() {
        return cartDao.getList();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        List<CartProduct> products = getCartProducts();
        for (CartProduct product : products) {
            totalQuantity += product.getQuantity();
        }
        return totalQuantity;
    }

    public double getTotalPrice() {
        double total = 0.0;
        List<CartProduct> products = getCartProducts();
        for (CartProduct product : products) {
            total += product.getQuantity() * product.getPrice();
        }
        return total;
    }
}
