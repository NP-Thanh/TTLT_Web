package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.model.ProductType;

import java.util.List;

public class ProductServiece {
    static ProductDao productDao = new ProductDao();

    public List<ProductType> getAllProductTypes() {
        return productDao.getAllTypes();
    }
}
