package vn.edu.hcmuaf.fit.web.dao.cart;

import java.io.Serializable;
import java.sql.Timestamp;

public class CartProduct implements Serializable {
    private int cart_id;         // ID của giỏ hàng
    private int user_id;
    private String userName;
    private int product_id;
    private String productName;
    private double price;
    private int quantity;
    private String duration;
    private String image;
    private Timestamp created_at;

    public CartProduct() {
    }

    public CartProduct(int cart_id, String productName, double price, int quantity, String duration, String image) {
        this.cart_id = cart_id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.duration = duration;
        this.image = image;
    }

    public CartProduct(int cart_id, String userName, String productName, int quantity, double price, Timestamp created_at) {
        this.cart_id = cart_id;
        this.userName = userName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
    }

    public int getCartId(){
        return cart_id;
    }
    public void setCartId(int cartId){
        this.cart_id = cartId;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
