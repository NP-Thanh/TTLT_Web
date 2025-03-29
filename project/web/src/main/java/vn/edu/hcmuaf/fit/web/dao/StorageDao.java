package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.KeyManage;
import vn.edu.hcmuaf.fit.web.model.ProductManage;
import vn.edu.hcmuaf.fit.web.model.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageDao {
    public List<Storage> getAvailableKeys(int pid, int quantity) {
        // Truy vấn lấy key chưa sử dụng
        return JDBIConnector.getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM storage WHERE product_id = ? AND order_id IS NULL AND status = 'Chưa xuất' LIMIT ?")
                        .bind(0, pid)
                        .bind(1, quantity)
                        .mapToBean(Storage.class)
                        .list()
        );
    }

    public void updateKeyStatus(int keyId, int oid, String status) {
        // Cập nhật trạng thái key
        JDBIConnector.getJdbi().withHandle(h -> {
            return h.createUpdate("UPDATE storage SET order_id = ?, status = ? WHERE id = ?")
                    .bind(0, oid)
                    .bind(1, status)
                    .bind(2, keyId)
                    .execute();
        });
    }
    public List<KeyManage> getAllKeys() {
        List<KeyManage> keys = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery("SELECT s.id, s.key, " +
                                "p.name AS productName, pt.type AS productType, s.status, " +
                                "p.image " +
                                "FROM storage s " +
                                "JOIN products p ON s.product_id = p.id " +
                                "JOIN product_types pt ON p.type_id = pt.id")
                    .map((rs, ctx) -> new KeyManage(
                            rs.getInt("id"),
                            rs.getString("key"),
                            rs.getString("productName"),
                            rs.getString("productType"),
                            rs.getString("status"),
                            rs.getString("image")

                    )).list();
        });
        return keys;
    }

    public void deleteKey(int keyId) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("DELETE FROM storage WHERE id = :id")
                        .bind("id", keyId)
                        .execute()
        );
    }

    public void editKey(int id, String key, String productName, String productType, String image){
        JDBIConnector.getJdbi().useHandle(handle -> {
            handle.createUpdate("UPDATE storage SET `key` = :key WHERE id = :id")
                    .bind("id", id)
                    .bind("key", key)
                    .bind("productName", productName)
                    .bind("productType", productType)
                    .bind("image", image)
                    .execute();
        });
    }
}
