package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Storage;

import java.util.List;

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
}
