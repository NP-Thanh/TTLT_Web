package vn.edu.hcmuaf.fit.web.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;

import java.util.List;

public class CartDao {
    private Jdbi jdbi = JDBIConnector.getJdbi();

    public int createCart(int userId) {
        String sql = "INSERT INTO carts (user_id, created_at) VALUES (?, CURRENT_TIMESTAMP)";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, userId)
                        .executeAndReturnGeneratedKeys()
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(-1)
        );
    }

    public boolean addCartDetail(CartProduct cartProduct) {
        String sql = "INSERT INTO cart_detail (cart_id, product_id, price, quantity, created_at) VALUES (?, ?, ?, ?, now())";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, cartProduct.getCartId())
                        .bind(1, cartProduct.getProduct_id())
                        .bind(2, cartProduct.getPrice())
                        .bind(3, cartProduct.getQuantity())
                        .execute() > 0
        );
    }

    public boolean updateCartDetail(CartProduct cartProduct) {
        String sql = "UPDATE cart_detail SET quantity = ?, price = ? WHERE cart_id = ? AND product_id = ?";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, cartProduct.getQuantity())
                        .bind(1, cartProduct.getPrice())
                        .bind(2, cartProduct.getCartId())
                        .bind(3, cartProduct.getProduct_id())
                        .execute() > 0
        );
    }

    public boolean removeCartDetail(int cartId, int productId) {
        String sql = "DELETE FROM cart_detail WHERE cart_id = ? AND product_id = ?";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, cartId)
                        .bind(1, productId)
                        .execute() > 0
        );
    }

    public List<CartProduct> getCartDetails(int cartId) {
        String sql = "SELECT cd.cart_id, cd.product_id, cd.price, cd.quantity, p.name AS productName, p.image, p.duration " +
                "FROM cart_detail cd JOIN products p ON cd.product_id = p.id " +
                "WHERE cd.cart_id = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, cartId)
                        .mapToBean(CartProduct.class)
                        .list()
        );
    }

    public Cart getCartByUserId(int userId) {
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, userId)
                        .mapToBean(Cart.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean deleteCart(int cartId) {
        String sql = "DELETE FROM carts WHERE id = ?";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, cartId)
                        .execute() > 0
        );
    }

    public List<CartProduct> getAllListCartDetails() {
        List<CartProduct> cart = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery("SELECT c.id as cartId, u.name AS userName, " +
                            "p.name AS productName, ct.quantity, ct.price, ct.created_at " +
                            "FROM carts c " +
                            "JOIN cart_detail ct ON c.id = ct.cart_id " +
                            "JOIN products p ON p.id = ct.product_id " +
                            "JOIN users u ON c.user_id = u.id")
                    .map((rs, ctx) -> new CartProduct(
                            rs.getInt("cartId"),
                            rs.getString("userName"),
                            rs.getString("productName"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getTimestamp("created_at")
                    )).list();
        });
        return cart;
    }
}