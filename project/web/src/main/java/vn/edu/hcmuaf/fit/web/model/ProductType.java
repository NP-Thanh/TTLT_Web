package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;

public class ProductType implements Serializable {
    private String id;
    private String type;

    public ProductType() {
    }

    public ProductType(String id, String name) {
        this.id = id;
        this.type = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
