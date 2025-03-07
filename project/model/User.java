package vn.edu.hcmuaf.fit.demo.dao.model;

import java.time.LocalDateTime;

public class User {
    private int userID; // ID người dùng
    private String name;
    private int roleID; // ID vai trò
    private String email;
    private String phone;
    private String password;
    private LocalDateTime regisDate; // Ngày đăng ký
    private String status;

    public User(int userID, String name, int roleID, String email, String phone, String password, LocalDateTime regisDate, String status) {
        this.userID = userID;
        this.name = name;
        this.roleID = roleID;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.regisDate = regisDate;
        this.status = status;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    // Getters và Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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

    public LocalDateTime getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(LocalDateTime regisDate) {
        this.regisDate = regisDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}