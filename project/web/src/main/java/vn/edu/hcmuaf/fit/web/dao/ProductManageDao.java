package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.ProductManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ProductManageDao {
    public List<ProductManage> getProductList() {
        List<ProductManage> products = JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("SELECT " +
                            "p.id, " +
                            "pt.type AS type_name, " +
                            "p.name, " +
                            "p.price, " +
                            "p.duration, " +
                            "(SELECT COUNT(*) FROM storage s WHERE s.product_id = p.id AND s.order_id IS NULL) AS quantity, " +
                            "p.image, " +
                            "CASE WHEN (SELECT COUNT(*) FROM storage s WHERE s.product_id = p.id AND s.order_id IS NULL) = 0 THEN 'hết hàng' ELSE p.status END AS status_display, " +
                            "pd.description, " +
                            "pd.introduction, " +
                            "pd.manufacturer, " +
                            "pd.support " +
                            "FROM products p " +
                            "JOIN product_types pt ON p.type_id = pt.id " +
                            "JOIN product_detail pd ON p.id = pd.product_id")
                    .map((rs, ctx) -> new ProductManage(
                            rs.getInt("id"),
                            rs.getString("type_name"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("duration"),
                            rs.getInt("quantity"),
                            rs.getString("image"),
                            rs.getString("status_display"),
                            rs.getString("description"),
                            rs.getString("introduction"),
                            rs.getString("manufacturer"),
                            rs.getString("support")
                    ))
                    .list();
        });
        return products;
    }
    public void editProduct(int id, String name, String type_name, double price, String duration,
                            String img, String des, String introduction, String manufacturer, String support) {
        JDBIConnector.getJdbi().useHandle(handle -> {

            // Lấy dữ liệu trước khi cập nhật
            String beforeData = handle.createQuery("SELECT p.name, t.type AS type_name, p.price, p.duration, p.image, " +
                            "d.description, d.introduction, d.manufacturer, d.support " +
                            "FROM products p " +
                            "JOIN product_types t ON p.type_id = t.id " +
                            "JOIN product_detail d ON p.id = d.product_id " +
                            "WHERE p.id = :id")
                    .bind("id", id)
                    .mapToMap()
                    .findOne()
                    .map(JSONObject::new)
                    .map(JSONObject::toString)
                    .orElse("{}");

            handle.createUpdate("UPDATE products SET name = :name, type_id = (SELECT id FROM product_types WHERE type like :type_name LIMIT 1), price = :price, " +
                            "duration = :duration, image = :img WHERE id = :id")
                    .bind("id", id)
                    .bind("name", name)
                    .bind("type_name", type_name) // Assuming type_name is mapped to type_id
                    .bind("price", price)
                    .bind("duration", duration)
                    .bind("img", img)
                    .execute();

            handle.createUpdate("UPDATE product_detail SET description = :des, introduction = :introduction, " +
                            "manufacturer = :manufacturer, support = :support WHERE product_id = :id")
                    .bind("id", id)
                    .bind("des", des)
                    .bind("introduction", introduction)
                    .bind("manufacturer", manufacturer)
                    .bind("support", support)
                    .execute();

            // 4️⃣ Lấy dữ liệu sau khi cập nhật
            String afterData = handle.createQuery("SELECT p.name, t.type AS type_name, p.price, p.duration, p.image, " +
                            "d.description, d.introduction, d.manufacturer, d.support " +
                            "FROM products p " +
                            "JOIN product_types t ON p.type_id = t.id " +
                            "JOIN product_detail d ON p.id = d.product_id " +
                            "WHERE p.id = :id")
                    .bind("id", id)
                    .mapToMap()
                    .findOne()
                    .map(JSONObject::new)
                    .map(JSONObject::toString)
                    .orElse("{}");

            // 5️⃣ Lưu log vào bảng `log_entry`
            handle.createUpdate("INSERT INTO log_entry (level, action, who, before_data, after_data) VALUES ( :level, :action, :who, :before_data, :after_data)")
                    .bind("level", "Info")
                    .bind("action", "update product")
                    .bind("who", 7)
                    .bind("before_data", beforeData)
                    .bind("after_data", afterData)
                    .execute();
        });
    }
    public int addProduct(String name, String type_name, double price, String duration,
                           String img, String des, String introduction, String manufacturer, String support, String banner) {
        return JDBIConnector.getJdbi().withHandle(handle -> {

            // Insert into products table
            handle.createUpdate("INSERT INTO products (name, type_id, price, duration, image) " +
                            "VALUES (:name, (SELECT id FROM product_types WHERE type LIKE :type_name LIMIT 1), " +
                            ":price, :duration, :img)")
                    .bind("name", name)
                    .bind("type_name", type_name)
                    .bind("price", price)
                    .bind("duration", duration)
                    .bind("img", img)
                    .execute();

            // Get the generated id of the newly inserted product
            int generatedId = handle.createQuery("SELECT LAST_INSERT_ID()")
                    .mapTo(int.class)
                    .findOnly();

            // Insert into product_detail table using the generated id
            handle.createUpdate("INSERT INTO product_detail (product_id, description, introduction, manufacturer, support, banner) " +
                            "VALUES (:id, :des, :introduction, :manufacturer, :support, :banner)")
                    .bind("id", generatedId)  // Use the generated id
                    .bind("des", des)
                    .bind("introduction", introduction)
                    .bind("manufacturer", manufacturer)
                    .bind("support", support)
                    .bind("banner", banner)
                    .execute();

            return generatedId;
        });
    }
    public void addProductKey(int pid,String key){
        JDBIConnector.getJdbi().useHandle(handle -> {
            handle.createUpdate("INSERT INTO storage (product_id, status,date,`key`) VALUES (:pid,'Chưa xuất',now(),:key)")
                    .bind("pid", pid)
                    .bind("key", key)
                    .execute();
        });
    }

    public List<ProductManage> filterProducts(Integer productId, String productName, String status) {
        StringBuilder query = new StringBuilder("SELECT DISTINCT " +
                "p.id, " +
                "pt.type AS type_name, " +
                "p.name, " +
                "p.price, " +
                "p.duration, " +
                "(SELECT COUNT(*) FROM storage s WHERE s.product_id = p.id AND s.order_id IS NULL) AS quantity, " +
                "p.image, " +
                "pd.description, " +
                "pd.introduction, " +
                "pd.manufacturer, " +
                "pd.support " +
                "FROM products p " +
                "JOIN product_types pt ON p.type_id = pt.id " +
                "JOIN product_detail pd ON p.id = pd.product_id " +
                "LEFT JOIN storage s ON p.id = s.product_id " +
                "WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();

        // Kiểm tra và thêm điều kiện nếu có
        if (productId != null) {
            query.append("AND p.id = :productId ");
            params.put("productId", productId);
        }
        if (productName != null && !productName.trim().isEmpty()) {
            query.append("AND p.name LIKE :productName ");
            params.put("productName", "%" + productName + "%");
        }
        if (status != null && !status.trim().isEmpty()) {
            if (status.equals("Còn hàng")) {
                query.append("AND (SELECT COUNT(*) FROM storage s2 WHERE s2.product_id = p.id AND s2.order_id IS NULL) > 0 ");
            } else if (status.equals("Hết hàng")) {
                query.append("AND (SELECT COUNT(*) FROM storage s2 WHERE s2.product_id = p.id AND s2.order_id IS NULL) = 0 ");
            }
        }

        return JDBIConnector.getJdbi().withHandle(h -> {
            var q = h.createQuery(query.toString());

            // 🔥 Fix lỗi bind tham số
            params.forEach((key, value) -> q.bind(key, value));

            return q.map((rs, ctx) -> {
                int quantity = rs.getInt("quantity");
                return new ProductManage(
                        rs.getInt("id"),
                        rs.getString("type_name"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("duration"),
                        quantity,
                        rs.getString("image"),
                        quantity > 0 ? "còn hàng" : "hết hàng", // Xác định trạng thái trong Java
                        rs.getString("description"),
                        rs.getString("introduction"),
                        rs.getString("manufacturer"),
                        rs.getString("support")
                );
            }).list();
        });
    }

    //    Quản lý sản phẩm phân quyền admin
    // Lấy danh sách product_type mà admin (user_id) có quyền
    public List<Integer> getPermittedTypeIdsByUserId(int userId){
        String query = "SELECT DISTINCT pt.id " +
                "FROM users u " +
                "JOIN role_permission rp ON u.role_id = rp.role_id " +
                "JOIN permission p ON rp.permission_id = p.id "+
                "JOIN product_types pt ON LOWER(p.permit) LIKE CONCAT('Manage ', LOWER(pt.type), ' Product') " +
                "WHERE u.id = :userId";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(query)
                        .bind("userId", userId)
                        .mapTo(Integer.class)
                        .list());
    }

    // Lấy danh sách sản phẩm theo danh sách type_id được phép
    public List<ProductManage> getProductListByTypeIds(List<Integer> typeIds){
        if (typeIds == null || typeIds.isEmpty())
            return List.of();

        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT " +
                                "p.id, " +
                                "pt.type AS type_name, " +
                                "p.name, " +
                                "p.price, " +
                                "p.duration, " +
                                "(SELECT COUNT(*) FROM storage s WHERE s.product_id = p.id AND s.order_id IS NULL) AS quantity, " +
                                "p.image, " +
                                "CASE WHEN (SELECT COUNT(*) FROM storage s WHERE s.product_id = p.id AND s.order_id IS NULL) = 0 THEN 'hết hàng' ELSE p.status END AS status_display, " +
                                "pd.description, " +
                                "pd.introduction, " +
                                "pd.manufacturer, " +
                                "pd.support " +
                                "FROM products p " +
                                "JOIN product_types pt ON p.type_id = pt.id " +
                                "JOIN product_detail pd ON p.id = pd.product_id " +
                                "WHERE p.type_id IN (<typeIds>)")
                        .bindList("typeIds", typeIds)
                        .map((rs, ctx) -> new ProductManage(
                                rs.getInt("id"),
                                rs.getString("type_name"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getString("duration"),
                                rs.getInt("quantity"),
                                rs.getString("image"),
                                rs.getString("status_display"),
                                rs.getString("description"),
                                rs.getString("introduction"),
                                rs.getString("manufacturer"),
                                rs.getString("support")
                        ))
                        .list()
        );
    }

    public List<ProductManage> getProductListForUser(int userId){
        List<Integer> typeIds = getPermittedTypeIdsByUserId(userId);
        return getProductListByTypeIds(typeIds);
    }

    public List<ProductManage> getProductListByRole(int userId, boolean isSuperAdmin){
        return isSuperAdmin ? getProductList() : getProductListForUser(userId);
    }

    //Cho admin chỉ lấy ra lựa chọn type theo phân quyền của mình
    public List<String> getProductTypeByUserId(int uid) {
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT pt.type " +
                                    "FROM users u " +
                                    "JOIN role r ON u.role_id = r.id " +
                                    "JOIN role_permission rp ON rp.role_id = r.id " +
                                    "JOIN permission p ON rp.permission_id = p.id " +
                                    "JOIN product_types pt ON p.permit LIKE CONCAT('Manage ', LOWER(pt.type), ' Product' ) " +
                                    "WHERE u.id = :uid "
        )
                        .bind("uid", uid)
                        .mapTo(String.class)
                        .list()
        );
    }

}
