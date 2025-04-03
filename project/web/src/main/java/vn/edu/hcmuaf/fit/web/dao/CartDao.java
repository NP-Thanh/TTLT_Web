package vn.edu.hcmuaf.fit.web.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
public class CartDao {
    private Connection connection;

    public CartDao(Connection connection) {
        this.connection = connection;
    }

    public boolean add(CartProduct cartProduct) {
        String sql = "INSERT INTO cart_products (id, product_name, price, quantity, duration, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartProduct.getId());
            stmt.setString(2, cartProduct.getProductName());
            stmt.setDouble(3, cartProduct.getPrice());
            stmt.setInt(4, cartProduct.getQuantity());
            stmt.setString(5, cartProduct.getDuration());
            stmt.setString(6, cartProduct.getImage());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sub(int id) {
        String sql = "UPDATE cart_products SET quantity = quantity - 1 WHERE id = ? AND quantity > 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CartProduct cartProduct) {
        String sql = "UPDATE cart_products SET quantity = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartProduct.getQuantity());
            stmt.setInt(2, cartProduct.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        String sql = "DELETE FROM cart_products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CartProduct> getList() {
        List<CartProduct> cartProducts = new ArrayList<>();
        String sql = "SELECT * FROM cart_products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CartProduct cartProduct = new CartProduct();
                cartProduct.setId(rs.getInt("id"));
                cartProduct.setProductName(rs.getString("product_name"));
                cartProduct.setPrice(rs.getDouble("price"));
                cartProduct.setQuantity(rs.getInt("quantity"));
                cartProduct.setDuration(rs.getString("duration"));
                cartProduct.setImage(rs.getString("image"));
                cartProducts.add(cartProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }
}
