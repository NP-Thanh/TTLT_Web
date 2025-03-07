package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Bank;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductDetail;
import vn.edu.hcmuaf.fit.web.model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
        public List<ProductType> getAllTypes() {
            List<ProductType> types= JDBIConnector.getJdbi().withHandle(h->{
                return h.createQuery("select * from product_types").mapToBean(ProductType.class).list();
            });
            return types;
        }

    public Product getProductById(int id) {
        Product product= (Product) JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("select * from products where id= :id").bind("id", id).mapToBean(Product.class).findOne().orElse(null);
        });
        return product;
    }

    public ProductDetail getProductDetailById(int id) {
        ProductDetail productDetail= (ProductDetail) JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("select * from product_detail where product_id= :id").bind("id", id).mapToBean(ProductDetail.class).findOne().orElse(null);
        });
        return productDetail;
    }

    public List<Product> getRelatedProducts(int id) {
        return JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery("""
                SELECT p.* 
                FROM products p
                JOIN product_detail pd ON p.id = pd.product_id
                WHERE p.id != :productId
                    AND (
                        (p.type_id = :typeId AND pd.manufacturer = :manufacturer)
                        OR (p.type_id = :typeId)
                    )
                ORDER BY
                   (p.type_id = :typeId AND pd.manufacturer = :manufacturer) DESC,
                   p.create_at DESC
                LIMIT 6
            """)
                    .bind("typeId", getProductById(id).getTypeId())
                    .bind("manufacturer", getProductDetailById(id).getManufacturer())
                    .bind("productId", id)
                    .mapToBean(Product.class)
                    .list();
        });
    }
    // Lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(String category) {
        List<Product> productsWithCategory = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT * FROM products p WHERE p.type_id IN (SELECT id FROM product_types pt WHERE pt.type LIKE :category)"
                    )
                    .bind("category", category) // Bind tham số với ký tự wildcard nếu sử dụng LIKE
                    .mapToBean(Product.class)
                    .list();
        });
        return productsWithCategory;
    }
    public List<Product> getProductsByPrice(double start,double end) {
        List<Product> productsWithPrice = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT * FROM products WHERE price BETWEEN :startPrice AND :endPrice;"
                    )
                    .bind("startPrice", start)
                    .bind("endPrice",end)// Bind tham số với ký tự wildcard nếu sử dụng LIKE
                    .mapToBean(Product.class)
                    .list();
        });
        return productsWithPrice;
    }
    public List<Product> getProductsByQuantitySale() {
        List<Product> products = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT \n" +
                                    "    p.*,\n" +
                                    "    IFNULL(SUM(od.quantity), 0) AS total_quantity_sold\n" +
                                    "FROM \n" +
                                    "    products p\n" +
                                    "LEFT JOIN \n" +
                                    "    order_detail od ON p.id = od.product_id\n" +
                                    "LEFT JOIN \n" +
                                    "    orders o ON od.order_id = o.id AND o.status = 'Đã thanh toán'\n" +
                                    "GROUP BY \n" +
                                    "    p.id, p.name, p.price\n" +
                                    "ORDER BY \n" +
                                    "    total_quantity_sold DESC LIMIT 10;\n"
                    )
                    .mapToBean(Product.class)
                    .list();
        });
        return products;
    }
    public List<Product> getProductsByQuantitySaleNoLimit() {
        List<Product> products = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT \n" +
                                    "    p.*,\n" +
                                    "    IFNULL(SUM(od.quantity), 0) AS total_quantity_sold\n" +
                                    "FROM \n" +
                                    "    products p\n" +
                                    "LEFT JOIN \n" +
                                    "    order_detail od ON p.id = od.product_id\n" +
                                    "LEFT JOIN \n" +
                                    "    orders o ON od.order_id = o.id AND o.status = 'Đã thanh toán'\n" +
                                    "GROUP BY \n" +
                                    "    p.id, p.name, p.price\n" +
                                    "ORDER BY \n" +
                                    "    total_quantity_sold DESC"
                    )
                    .mapToBean(Product.class)
                    .list();
        });
        return products;
    }

    public List<Product> getProductsByCreateAt() {
        List<Product> products = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT * FROM products order by create_at DESC "
                    )

                    .mapToBean(Product.class)
                    .list();
        });
        return products;
    }

    public List<Product> getProductsByPriceASC() {
        List<Product> products = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT * FROM products order by price asc"
                    )

                    .mapToBean(Product.class)
                    .list();
        });
        return products;
    }
    public List<Product> getProductsByPriceDESC() {
        List<Product> products = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createQuery(
                            "SELECT * FROM products order by price DESC"
                    )

                    .mapToBean(Product.class)
                    .list();
        });
        return products;
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> produts= JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("SELECT * FROM products").mapToBean(Product.class).list();
        });
        return produts;
    }
    public List<Product> getMostViewedProducts(int limit) {
        List<Product> produts= JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("SELECT *, COUNT(vp.product_id) AS view_count FROM products p LEFT JOIN view_product vp ON p.id = vp.product_id GROUP BY p.id ORDER BY view_count DESC LIMIT ? ")
                    .bind(0,limit).mapToBean(Product.class).list();
        });
        return produts;
    }

    public List<Product> getMostViewedProductsNone(int offset, int limit) {
        List<Product> produts= JDBIConnector.getJdbi().withHandle(h-> {
            return h.createQuery("SELECT *, COUNT(vp.product_id) AS view_count FROM products p LEFT JOIN view_product vp ON p.id = vp.product_id GROUP BY p.id ORDER BY view_count DESC LIMIT ?,?")
                    .bind(0, offset)
                    .bind(1, limit)
                    .mapToBean(Product.class)
                    .list();
        });
        return produts;
    }
    public List<Product> getTypeProducts(int typeID, int limit) {
        List<Product> produts= JDBIConnector.getJdbi().withHandle(h-> {
            return h.createQuery("SELECT * FROM products p WHERE p.type_id = ? LIMIT ?")
                    .bind(0, typeID)
                    .bind(1, limit)
                    .mapToBean(Product.class)
                    .list();
        });
        return produts;
    }
    public List<Product> getSearchProducts(String search) {
        List<Product> produts= JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("SELECT * FROM products p WHERE p.name LIKE ?")
                    .bind(0, "%" + search + "%")
                    .mapToBean(Product.class)
                    .list();
        });
        return produts;
//        xử lí bank admin
    }
    public List<Bank> getBanks() {
            List<Bank> banks= JDBIConnector.getJdbi().withHandle(h->{
                return h.createQuery("select * from banks").mapToBean(Bank.class).list();
            });
            return banks;
    }
    public void deleteBank(int bid) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("DELETE FROM banks WHERE id = :id")
                        .bind("id", bid)
                        .execute()
        );
    }
    public void insertBank(int id, String name, String number, String owner, String qr) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("INSERT INTO banks (id, name, number, owner, qr) VALUES (:id, :name, :number, :owner, :qr)")
                        .bind("id", id)
                        .bind("name", name)
                        .bind("number", number)
                        .bind("owner", owner)
                        .bind("qr", qr)
                        .execute()
        );
    }
    public void editBank(int id, String name, String number, String owner, String qr) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("UPDATE banks SET name = :name, number = :number, owner = :owner, qr = :qr WHERE id = :id")
                        .bind("id", id)
                        .bind("name", name)
                        .bind("number", number)
                        .bind("owner", owner)
                        .bind("qr", qr)
                        .execute()
        );
    }
    //product management
    public void deleteProduct(int pid) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("DELETE FROM products WHERE id = :id")
                        .bind("id", pid)
                        .execute()
        );
    }

    public List<Product> getViewedProductsByUserId(int userId) {
        String sql = """
            SELECT p.* 
            FROM products p
            JOIN view_product v ON p.id = v.product_id
            WHERE v.user_id = :userId
            ORDER BY v.view_time DESC
        """;
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("userId", userId)
                        .mapToBean(Product.class)
                        .list()
        );
    }
}
