package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.CartDao;
import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.dao.ProductManageDao;
import vn.edu.hcmuaf.fit.web.dao.StorageDao;
import vn.edu.hcmuaf.fit.web.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.web.model.Bank;
import vn.edu.hcmuaf.fit.web.model.KeyManage;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductManage;

import java.util.List;

public class AdminService {
    static ProductDao productDao = new ProductDao();
    static ProductManageDao productManageDao = new ProductManageDao();
    static StorageDao storageDao = new StorageDao();
    static CartDao cartDao = new CartDao();

    public List<Bank> getAllBanks() {
        return productDao.getBanks();
    }
    public void deleteProduct(int pid) {
        productDao.deleteProduct(pid);
    }
    public List<ProductManage> getProductManageList() {
        return productManageDao.getProductList();
    }
    public void editProduct(int id, String name, String type_name, double price, String duration,
                            String img, String des, String introduction, String manufacturer, String support) {
        productManageDao.editProduct(id,name,type_name,price,duration,img,des,introduction,manufacturer,support);
    }
    public int addProduct(String name, String type_name, double price, String duration,
                           String img, String des, String introduction, String manufacturer, String support, String banner) {
        return productManageDao.addProduct(name, type_name, price, duration, img, des, introduction, manufacturer, support, banner);

    }
    public void addProductKey(int pid,String key){
        productManageDao.addProductKey(pid,key);
    }

    public List<ProductManage> filterProducts(Integer productId, String productName, String status) {
        return productManageDao.filterProducts(productId, productName, status);
    }

    public List<KeyManage> getKeyManageList() {
        return storageDao.getAllKeys();
    }
    public void deleteKeyManage(int id) {
        storageDao.deleteKey(id);
    }

    public void editKey(int id, String key, String productName, String productType, String image){
        storageDao.editKey(id,key,productName,productType,image);
    }
    public List<KeyManage> filterKeyManages(Integer keyId) {
        return storageDao.filterKey(keyId);
    }

    public List<CartProduct> getAllListCartDetails (){
        return cartDao.getAllListCartDetails();
    }

    public List<ProductManage> getProductListByRole(int userId, boolean isAdmin) {
        return productManageDao.getProductListByRole(userId, isAdmin);
    }

    public List<String> getProductTypeByUserId(int userId) {
        return productManageDao.getProductTypeByUserId(userId);
    }
}
