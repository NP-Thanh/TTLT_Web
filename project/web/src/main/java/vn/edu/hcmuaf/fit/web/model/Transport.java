package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;

public class Transport implements Serializable {
    private int orderId;
    private String transportation;
    private String province;
    private String district;
    private String subDistrict;
    private String specificAddress;
    private String status;

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getTransportation() { return transportation; }
    public void setTransportation(String transportation) { this.transportation = transportation; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getSubDistrict() { return subDistrict; }
    public void setSubDistrict(String subDistrict) { this.subDistrict = subDistrict; }

    public String getSpecificAddress() { return specificAddress; }
    public void setSpecificAddress(String specificAddress) { this.specificAddress = specificAddress; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
