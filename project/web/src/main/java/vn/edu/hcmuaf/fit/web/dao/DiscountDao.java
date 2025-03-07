package vn.edu.hcmuaf.fit.web.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Discount;

import java.util.List;
import java.util.Optional;

public class DiscountDao {
    private Jdbi jdbi;

    public DiscountDao() {
        this.jdbi = JDBIConnector.getJdbi();
        ;
    }

    public Discount addDiscount(Discount discount) {
        String sql = """
                    INSERT INTO discount (id, value, quantity, create_date, exp_date, status)
                    VALUES (:id, :value, :quantity, :createDate, :expDate, :status)
                """;

        try {
            int rowsAffected = jdbi.withHandle(handle ->
                    handle.createUpdate(sql)
                            .bindBean(discount)  // Ánh xạ discount
                            .execute()
            );

            if (rowsAffected > 0) {
                return discount;
            }
            throw new RuntimeException("Failed to insert discount");
        } catch (UnableToExecuteStatementException e) {
            throw new RuntimeException("Database error while adding discount: " + e.getMessage(), e);
        }
    }

    public Discount getDiscountById(int id) {
        String sql = "SELECT * FROM discount WHERE id = :id";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Discount.class)
                        .findOne().orElse(null)
        );

    }

    public List<Discount> getAllDiscounts() {
        String sql = "SELECT * FROM discount";

        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(sql)
                            .mapToBean(Discount.class)
                            .list()
            );
        } catch (UnableToExecuteStatementException e) {
            throw new RuntimeException("Database error while getting all discounts: " + e.getMessage(), e);
        }
    }

    public Discount updateDiscount(Discount discount) {
        JDBIConnector.getJdbi().withHandle(h -> {
            h.createUpdate("""
                            UPDATE discount
                            SET value = :value,
                                quantity = :quantity,
                                create_date = :createDate,
                                exp_date = :expiredDate,
                                status = :status
                            WHERE id = :id
                            """)
                    .bind("value", discount.getValue())
                    .bind("quantity", discount.getQuantity())
                    .bind("createDate", discount.getCreateDate()) // Đảm bảo rằng bạn đang cung cấp giá trị này
                    .bind("expiredDate", discount.getExpDate())
                    .bind("status", discount.getStatus())
                    .bind("id", discount.getId())
                    .execute();
            return null;
        });
        return discount;
    }

    public boolean deleteDiscount(int id) {
        String sql = "DELETE FROM discount WHERE id = :id";

        try {
            int rowsAffected = jdbi.withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("id", id)
                            .execute()
            );

            return rowsAffected > 0;
        } catch (UnableToExecuteStatementException e) {
            throw new RuntimeException("Database error while deleting discount: " + e.getMessage(), e);
        }
    }

}