package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Transport;

public class TransportDao {

    public Transport getTransportByOrderId(int orderId) {
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT * FROM transport WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(Transport.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public void insertTransport(int orderId, String transportation) {
        String sql = "INSERT INTO transport (order_id, transportation, province, district, sub_district, specific_address, status) " +
                "VALUES (:orderId, :transportation, NULL, NULL, NULL, NULL, NULL)";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("orderId", orderId)
                        .bind("transportation", transportation)
                        .execute()
        );
    }

    public void updateProvince(int orderId, String province) {
        String sql = "UPDATE transport SET province = :province, district = NULL, sub_district = NULL WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("province", province)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    public void updateDistrict(int orderId, String district) {
        String sql = "UPDATE transport SET district = :district, sub_district = NULL WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("district", district)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    public void updateSubDistrict(int orderId, String subDistrict) {
        String sql = "UPDATE transport SET sub_district = :subDistrict WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("subDistrict", subDistrict)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    public void updateSpecificAddress(int orderId, String specificAddress) {
        String sql = "UPDATE transport SET specific_address = :specificAddress WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("specificAddress", specificAddress)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    public void updateStatus(int orderId, String status) {
        String sql = "UPDATE transport SET status = :status WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("status", status)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    public void updateTransportation(int orderId, String transportation) {
        String sql = "UPDATE transport SET transportation = :transportation, province = NULL, district = NULL, sub_district = NULL, specific_address = NULL WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("transportation", transportation)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    public void deleteTransport(int orderId) {
        String sql = "DELETE FROM transport WHERE order_id = :orderId";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("orderId", orderId)
                        .execute()
        );
    }
}
