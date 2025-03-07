package vn.edu.hcmuaf.fit.web.model;

import java.time.LocalDate;

public class Discount {
    private Integer id;
    private Double value;
    private Integer quantity;
    private LocalDate createDate;
    private LocalDate expDate;
    private String status;

    // Constructor không tham số
    public Discount() {
    }

    // Constructor có tham số
    public Discount(Integer id, Double value, Integer quantity, LocalDate createDate, LocalDate expDate, String status) {
        this.id = id;
        this.value = value;
        this.quantity = quantity;
        this.createDate = createDate;
        this.expDate = expDate;
        this.status = status;
    }

    // Getter cho id
    public Integer getId() {
        return id;
    }

    // Setter cho id
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter cho value
    public Double getValue() {
        return value;
    }

    // Setter cho value
    public void setValue(Double value) {
        this.value = value;
    }

    // Getter cho quantity
    public Integer getQuantity() {
        return quantity;
    }

    // Setter cho quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Getter cho createDate
    public LocalDate getCreateDate() {
        return createDate;
    }

    // Setter cho createDate
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    // Getter cho expDate
    public LocalDate getExpDate() {
        return expDate;
    }

    // Setter cho expDate
    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    // Getter cho status
    public String getStatus() {
        return status;
    }

    // Setter cho status
    public void setStatus(String status) {
        this.status = status;
    }

    // Phương thức toString
    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", value=" + value +
                ", quantity=" + quantity +
                ", createDate=" + createDate +
                ", expDate=" + expDate +
                ", status='" + status + '\'' +
                '}';
    }
}