package vn.edu.hcmuaf.fit.web.model;

public class Bank {
    private int id;
    private String name;
    private String number;
    private String owner;
    private String qr;
    public Bank(int id, String name, String number, String owner, String qr) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.owner = owner;
        this.qr = qr;
    }
    public Bank(){

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}

