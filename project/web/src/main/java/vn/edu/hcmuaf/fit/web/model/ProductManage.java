package vn.edu.hcmuaf.fit.web.model;

import java.sql.Timestamp;

public class ProductManage {
    private int id;
    private String name;
    private int typeId;
    private String type_name;
    private int quantity;
    private double price;
    private String status; // "còn hàng" hoặc "hết hàng"
    private String duration;
    private String image;
    private Timestamp createAt;
    private int product_id;
    private String description;
    private String introduction;
    private String manufacturer;
    private String support;
    private String banner;
    public ProductManage() {}

    public ProductManage(int id, String name, int typeId, String type_name, int quantity, double price, String status, String duration, String image, Timestamp createAt, int product_id, String description, String introduction, String manufacturer, String support, String banner) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.type_name = type_name;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.duration = duration;
        this.image = image;
        this.createAt = createAt;
        this.product_id = product_id;
        this.description = description;
        this.introduction = introduction;
        this.manufacturer = manufacturer;
        this.support = support;
        this.banner = banner;
    }
    public ProductManage(int id, String type_name, String name,double price,String duration,
                         int quantity, String image, String status,
                          String description, String introduction,
                         String manufacturer, String support) {
        this.id = id;
        this.type_name = type_name;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
        this.description = description;
        this.introduction = introduction;
        this.manufacturer = manufacturer;
        this.support = support;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
