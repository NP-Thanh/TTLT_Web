package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Order;
import vn.edu.hcmuaf.fit.web.model.OrderDetail;

import java.util.List;

public class OrderDao {
    public Order getOrderById(int id) {
        Order order = (Order) JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("select * from orders where id= :id")
                    .bind("id", id)
                    .mapToBean(Order.class)
                    .findOne().orElse(null);
        });
        return order;
    }

    public List<OrderDetail> getOrderDetailByOID(int oid) {
        List<OrderDetail> order = (List<OrderDetail>) JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("select * from order_detail where order_id= :oid")
                    .bind("oid", oid)
                    .mapToBean(OrderDetail.class)
                    .list();
        });
        return order;
    }

    public List<Order> getAllOrderWithUID(int userID) {
        return JDBIConnector.getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM orders WHERE user_id = ?")
                        .bind(0, userID)
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public List<Order> getAllOrders() {
        return JDBIConnector.getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM orders")
                        .mapToBean(Order.class)
                        .list()
        );
    }

    // Insert đơn hàng vào bảng orders
    public int insertOrder(int userId, Integer discountId, double totalAmount) {
        String sql = "INSERT INTO orders (user_id, bank_id, discount_id, total_amount, status) VALUES (?, 1, ?, ?, 'Chưa thanh toán')";

        // Chèn đơn hàng vào bảng orders và lấy generated order_id
        return JDBIConnector.getJdbi().withHandle(h -> {
            return h.createUpdate(sql)
                    .bind(0, userId)
                    .bind(1, discountId != null ? discountId : null)
                    .bind(2, totalAmount)
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(int.class)
                    .findOnly();
        });
    }

    // Insert chi tiết đơn hàng vào bảng order_detail
    public void insertOrderDetail(int orderId, int productId, int quantity, double price) {
        String sql = "INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        // Chèn chi tiết đơn hàng vào bảng order_detail
        JDBIConnector.getJdbi().withHandle(h -> {
            return h.createUpdate(sql)
                    .bind(0, orderId)
                    .bind(1, productId)
                    .bind(2, quantity)
                    .bind(3, price)
                    .execute();
        });
    }

    public void updateOrderStatus(int oid, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        JDBIConnector.getJdbi().withHandle(h -> {
            return h.createUpdate(sql)
                    .bind(0, status)
                    .bind(1, oid)
                    .execute();
        });
    }

    public void updateBankID(int oid, int bid) {
        String sql = "UPDATE orders SET bank_id = ? WHERE id = ?";
        JDBIConnector.getJdbi().withHandle(h -> {
            return h.createUpdate(sql)
                    .bind(0, bid)
                    .bind(1, oid)
                    .execute();
        });
    }

    public List<Order> filterOrders(Integer orderId, String productName, String status) {
        StringBuilder query = new StringBuilder("SELECT DISTINCT o.id AS id, o.user_id, o.bank_id, o.discount_id, o.total_amount, o.status ");
//        query.append("od.order_id AS order_detail_id, od.product_id, od.quantity, od.price, ");
        query.append("FROM orders o ");
        query.append("JOIN order_detail od ON o.id = od.order_id ");
        query.append("JOIN products p ON od.product_id = p.id WHERE 1=1 ");

        // Thêm điều kiện lọc vào câu truy vấn nếu có
        if (orderId != null) {
            query.append("AND o.id = :orderId ");
        }
        if (productName != null && !productName.trim().isEmpty()) {
            query.append("AND p.name LIKE :productName ");
        }
        if (status != null && !status.trim().isEmpty()) {
            query.append("AND o.status = :status ");
        }

        List<Order> orders = JDBIConnector.getJdbi().withHandle(h ->
                h.createQuery(query.toString())
                        .bind("orderId", orderId)
                        .bind("productName", "%" + productName + "%")
                        .bind("status", status)
                        .mapToBean(Order.class)
                        .list()
        );
        return orders;
    }

    public void deleteOrder(int id) {
        JDBIConnector.getJdbi().useTransaction(handle -> {
            // Xóa chi tiết đơn hàng trước
            handle.createUpdate("DELETE FROM order_detail WHERE order_id = :id")
                    .bind("id", id)
                    .execute();

            // Xóa đơn hàng
            handle.createUpdate("DELETE FROM orders WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }


}
