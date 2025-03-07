package vn.edu.hcmuaf.fit.web.dao.cart;

import java.io.Serializable;

public class CartProduct implements Serializable {
    private int id;
    private String productName;
    private double price;
    private int quantity;
    private String duration;
    private String image;

    public CartProduct() {
    }

    public CartProduct(int id, String productName, double price, int quantity, String duration, String image) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.duration = duration;
        this.image = image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
