package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Product implements Serializable {
    private int id;
    private String name;
    private int typeId;
    private String type_name;
    private double price;
    private String status; // "còn hàng" hoặc "hết hàng"
    private String duration;
    private String image;
    private Timestamp createAt;

    // Constructors
    public Product() {
    }

    public Product(int id, String name, int typeId,String type_name, double price, String status, String duration, String image, Timestamp createAt) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.type_name = type_name;
        this.price = price;
        this.status = status;
        this.duration = duration;
        this.image = image;
        this.createAt = createAt;
    }
    public Product(int id, String name, int typeId, double price, String status, String duration, String image, Timestamp createAt) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.price = price;
        this.status = status;
        this.duration = duration;
        this.image = image;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
