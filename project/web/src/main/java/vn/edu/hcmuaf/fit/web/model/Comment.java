package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    private int id;
    private int user_id;
    private int product_id;
    private int order_id;
    private int num_rate;
    private String comment;
    private Timestamp date;

    public Comment() {
    }

    public Comment(int id, int user_id, int product_id, int order_id, int num_rate, String comment, Timestamp date) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.order_id = order_id;
        this.num_rate = num_rate;
        this.comment = comment;
        this.date = date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getNum_rate() {
        return num_rate;
    }

    public void setNum_rate(int num_rate) {
        this.num_rate = num_rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
