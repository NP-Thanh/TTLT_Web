package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.dao.ProductManageDao;
import vn.edu.hcmuaf.fit.web.model.Bank;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductManage;

import java.util.List;

public class AdminService {
    static ProductDao productDao = new ProductDao();
    static ProductManageDao productManageDao = new ProductManageDao();

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
    public void addProduct(String name, String type_name, double price, String duration,
                           String img, String des, String introduction, String manufacturer, String support, String banner) {
        productManageDao.addProduct(name, type_name, price, duration, img, des, introduction, manufacturer, support, banner);

    }
    public void addProductKey(int pid,String key){
        productManageDao.addProductKey(pid,key);
    }

    public List<ProductManage> filterProducts(Integer productId, String productName, String status) {
        return productManageDao.filterProducts(productId, productName, status);
    }

}
