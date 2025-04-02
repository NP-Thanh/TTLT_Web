package vn.edu.hcmuaf.fit.web.model;

public class KeyManage {
    private int id;
    private String key;
    private String productName;
    private String productType;
    private String image;
    private int quantity;
    private String status;

    public KeyManage(int id, String key, String productName, String productType, String status, String image) {
        this.id = id;
        this.key = key;
        this.productName = productName;
        this.productType = productType;
        this.status = status;
        this.image = image;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
