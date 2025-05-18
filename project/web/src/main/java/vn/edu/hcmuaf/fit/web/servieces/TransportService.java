package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.TransportDao;
import vn.edu.hcmuaf.fit.web.model.Transport;

public class TransportService {
    private TransportDao transportDao = new TransportDao();

    public Transport getTransportByOrderId(int orderId) {
        return transportDao.getTransportByOrderId(orderId);
    }

    public void insertTransport(int orderId, String transportation) {
        transportDao.insertTransport(orderId, transportation);
    }

    public void updateProvince(int orderId, String province) {
        transportDao.updateProvince(orderId, province);
    }

    public void updateDistrict(int orderId, String district) {
        transportDao.updateDistrict(orderId, district);
    }

    public void updateSubDistrict(int orderId, String subDistrict) {
        transportDao.updateSubDistrict(orderId, subDistrict);
    }

    public void updateSpecificAddress(int orderId, String specificAddress) {
        transportDao.updateSpecificAddress(orderId, specificAddress);
    }

    public void updateStatus(int orderId, String status) {
        transportDao.updateStatus(orderId, status);
    }

    public void updateTransportation(int orderId, String transportation) {
        transportDao.updateTransportation(orderId, transportation);
    }

    public void deleteTransport(int orderId) {
        transportDao.deleteTransport(orderId);
    }

    public void updateFullAddress(int orderId, String province, String district, String subDistrict, String specificAddress) {
        transportDao.updateFullAddress(orderId, province, district, subDistrict, specificAddress);
    }
}
