package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.ProductManage;

import java.util.List;

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
                            "CASE WHEN (SELECT COUNT(*) FROM storage s WHERE s.product_id = p.id AND s.order_id IS NULL) = 0 THEN 'Hết hàng' ELSE p.status END AS status_display, " +
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
        });
    }
    public void addProduct(String name, String type_name, double price, String duration,
                           String img, String des, String introduction, String manufacturer, String support, String banner) {
        JDBIConnector.getJdbi().useHandle(handle -> {

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
}
