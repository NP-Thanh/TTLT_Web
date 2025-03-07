package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {
    private int id;
    private String name;
    private int role_id;
    private String email;
    private String phone;
    private String password;
    private Timestamp regis_date;
    private String status;

    public User() {
    }

    public User(int id, String name, int role_id, String email, String phone, String password, Timestamp regis_date, String status) {
        this.id = id;
        this.name = name;
        this.role_id = role_id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.regis_date = regis_date;
        this.status = status;
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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegis_date() {
        return regis_date;
    }

    public void setRegis_date(Timestamp regis_date) {
        this.regis_date = regis_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegis_date() {
    }
}