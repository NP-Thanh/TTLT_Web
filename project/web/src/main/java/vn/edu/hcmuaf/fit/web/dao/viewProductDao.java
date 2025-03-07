package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Product;

import java.util.List;

public class viewProductDao {
    private boolean check(int userId, int pid) {
        // Sử dụng JDBI để thực hiện truy vấn
        return JDBIConnector.getJdbi().withHandle(handle -> {
            // Truy vấn xem có bản ghi nào với user_id và product_id này không
            int count = handle.createQuery("SELECT COUNT(*) FROM view_product WHERE user_id = :user_id AND product_id = :product_id")
                    .bind("user_id", userId)
                    .bind("product_id", pid)
                    .mapTo(int.class)
                    .one();
            // Nếu có ít nhất 1 bản ghi, trả về true, nếu không trả về false
            return count > 0;
        });
    }

    public void insertViewProduct(int userId, int pid) {
        if(check(userId, pid)) {
            JDBIConnector.getJdbi().useHandle(handle ->
                    handle.createUpdate("update view_product set view_time = now() where user_id = :user_id and product_id = :product_id")
                            .bind("user_id", userId)
                            .bind("product_id", pid)
                            .execute()
            );
        }else{
            JDBIConnector.getJdbi().useHandle(handle ->
                    handle.createUpdate("INSERT INTO view_product (user_id, product_id,view_time) VALUES (:user_id, :product_id, now())")
                            .bind("user_id", userId)
                            .bind("product_id", pid)
                            .execute()
            );
        }
    }


}
