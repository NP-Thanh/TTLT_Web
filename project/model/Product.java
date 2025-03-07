package object;

import java.util.Date;

public class Product {

    private int id;
    private String name;
    private int type_id;
    private String type_name;
    private double price;
    private String status;
    private String img;
    private String duration;
    private Date create_at;

    public Product(int id, String name, int type_id, double price, String status, String img,String duration, Date create_at) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.price = price;
        this.status = status;
        this.img = img;
        this.duration = duration;
        this.create_at = create_at;
    }

    public Product(int id, String name, int type_id, String type_name, double price, String status, String img, String duration, Date create_at) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.type_name = type_name;
        this.price = price;
        this.status = status;
        this.img = img;
        this.duration = duration;
        this.create_at = create_at;
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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
}