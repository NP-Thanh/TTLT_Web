package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.dao.viewProductDao;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductType;

import java.util.List;

public class ListProduct {
    static ProductDao productDao = new ProductDao();
    static viewProductDao viewproductDao = new viewProductDao();

    public List<Product> getAllProduct() {
        return productDao.getAllProducts();
    }
    public List<Product> getAllProductWithType(String category) {
        return productDao.getProductsByCategory(category);
    }
    public List<Product> getMostViewedProduct(int limit) {
        return productDao.getMostViewedProducts(limit);
    }
    public List<Product> getMostViewedProductNone(int limit,int offset) {
        return productDao.getMostViewedProductsNone(limit,offset);
    }
    public List<Product> getTypeProducts(int typeID, int limit) {
        return productDao.getTypeProducts(typeID, limit);
    }
    public List<Product> getSearchProducts(String search) {
        return productDao.getSearchProducts(search);
    }
    public void viewProduct(int userId,int pID) {
        viewproductDao.insertViewProduct(userId,pID);
    }
    public List<Product> getProductsByPrice(double start,double end) {
        return productDao.getProductsByPrice(start,end);
    }
    public List<Product> getProductsByCreateAt( ) {
        return productDao.getProductsByCreateAt();
    }
    public List<Product> getProductsByPriceASC() {
        return productDao.getProductsByPriceASC();
    }
    public List<Product> getProductsByPriceDESC() {
        return productDao.getProductsByPriceDESC();
    }
    public List<Product> getProductsByQuantitySale() {
        return productDao.getProductsByQuantitySale();
    }
    public List<Product> getProductsByQuantitySaleNoLimit() {
        return productDao.getProductsByQuantitySaleNoLimit();
    }
}
