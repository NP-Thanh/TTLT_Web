package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;

public class ProductDetail implements Serializable {
    private int product_id;
    private String description;
    private String introduction;
    private String manufacturer;
    private String support;
    private String banner;

    // Constructors
    public ProductDetail() {
    }

    public ProductDetail(int product_id, String description, String introduction, String manufacturer, String support, String banner) {
        this.product_id = product_id;
        this.description = description;
        this.introduction = introduction;
        this.manufacturer = manufacturer;
        this.support = support;
        this.banner = banner;
    }

    // Getters and Setters
    public int getProductId() {
        return product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public void setProductId(int productId) {
        this.product_id = productId;
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


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}

