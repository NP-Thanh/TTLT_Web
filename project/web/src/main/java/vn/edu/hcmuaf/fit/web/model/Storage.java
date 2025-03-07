package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Storage implements Serializable {
    private int id; //key
    private int product_id;
    private int order_id;
    private String status;
    private Timestamp date;
    private String key;

    public Storage(int id, int product_id, int order_id, String status, Timestamp date, String key) {
        this.id = id;
        this.product_id = product_id;
        this.order_id = order_id;
        this.status = status;
        this.date = date;
        this.key = key;
    }

    public Storage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
