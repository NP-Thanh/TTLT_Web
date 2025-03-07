package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductDetail;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.ProductWithDetail;

import java.util.List;

public class ProductServiece {
    static ProductDao productDao = new ProductDao();

    public List<ProductType> getAllProductTypes() {
        return productDao.getAllTypes();
    }

    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    public ProductDetail getProductDetailById(int id) {
        return productDao.getProductDetailById(id);
    }

    public ProductWithDetail getProductWithDetailById(int id) {
        Product product = getProductById(id);
        ProductDetail productDetail = getProductDetailById(id);

        if (product != null) {
            return new ProductWithDetail(product, productDetail);
        }
        return null;
    }

    public List<Product> getRelatedProducts(int id) {
        return productDao.getRelatedProducts(id);
    }

    public List<Product> getViewedProductsByUserId(int userId) {
        return productDao.getViewedProductsByUserId(userId); // Gọi phương thức DAO
    }
}
