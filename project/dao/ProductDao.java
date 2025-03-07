package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.ProductType;

import java.util.List;

public class ProductDao {
    public List<ProductType> getAllTypes() {
        List<ProductType> types= JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("select * from product_types").mapToBean(ProductType.class).list();
        });
        return types;
    }
}
