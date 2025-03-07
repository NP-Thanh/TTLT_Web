package vn.edu.hcmuaf.fit.web.dao;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;

import java.util.List;
import java.util.Map;

public class DashboardDao {
    private Jdbi jdbi = JDBIConnector.getJdbi();

    public Map<String, Object> getInfo() {
        String query = "SELECT \n" +
                "    (SELECT COUNT(*) FROM users) AS so_luong_nguoi_dung,\n" +
                "    (SELECT COUNT(*) FROM products) AS so_luong_san_pham,\n" +
                "    (SELECT COUNT(*) FROM orders) AS so_luong_don_hang,\n" +
                "    (SELECT SUM(total_amount) FROM orders) AS tong_doanh_thu;\n";

        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToMap() // Ánh xạ kết quả vào một Map
                        .one()      // Lấy một kết quả duy nhất
        );
    }

    public List<Integer> getOrdersCountByMonth() {
        String query = "WITH months AS (\n" +
                "    SELECT 1 AS `month` UNION ALL\n" +
                "    SELECT 2 UNION ALL\n" +
                "    SELECT 3 UNION ALL\n" +
                "    SELECT 4 UNION ALL\n" +
                "    SELECT 5 UNION ALL\n" +
                "    SELECT 6 UNION ALL\n" +
                "    SELECT 7 UNION ALL\n" +
                "    SELECT 8 UNION ALL\n" +
                "    SELECT 9 UNION ALL\n" +
                "    SELECT 10 UNION ALL\n" +
                "    SELECT 11 UNION ALL\n" +
                "    SELECT 12\n" +
                ")\n" +
                "SELECT \n" +
                "    IFNULL(COUNT(o.`id`), 0) AS `order_count`\n" +
                "FROM \n" +
                "    months m\n" +
                "LEFT JOIN \n" +
                "    `orders` o\n" +
                "ON \n" +
                "    MONTH(o.`date`) = m.`month`\n" +
                "AND \n" +
                "    YEAR(o.`date`) = 2024\n" +
                "GROUP BY \n" +
                "    m.`month`\n" +
                "ORDER BY \n" +
                "    m.`month`;";

        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapTo(Integer.class)
                        .list()
        );
    }
}
