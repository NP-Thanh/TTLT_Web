package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.OrderDao;
import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.*;

import java.util.ArrayList;
import java.util.List;

public class OrderServiece {
    static OrderDao orderDao = new OrderDao();
    static UserDao userDao = new UserDao();
    static ProductDao productDao = new ProductDao();

    public Order getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    public List<OrderDetail> getOrderDetailByOID(int oid) {
        return orderDao.getOrderDetailByOID(oid);
    }

    public OrderWithUser getOrderWithUserByOID(int oid) {
        Order order= orderDao.getOrderById(oid);
        List<OrderDetail> orderDetails= orderDao.getOrderDetailByOID(oid);
        User user= userDao.getUserById(order.getUser_id());
        List<Product> products= new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            Product product= productDao.getProductById(orderDetail.getProduct_id());
            products.add(product);
        }
        return new OrderWithUser(order,orderDetails,user, products);
    }

    public OrderDetail getOrderDetail(OrderWithUser order, int pid) {
        for (OrderDetail detail: order.getOrderDetail()){
            if (detail.getProduct_id() == pid){
                return detail;
            }
        }
        return null;
    }

    public boolean checkProductInOrder(Product product, OrderWithUser order) {
        for (OrderDetail orderDetail : order.getOrderDetail()) {
            if (orderDetail.getProduct_id() == product.getId()){
                return true;
            }
        }
        return false;
    }

    public List<OrderWithUser> getAllOrderWithUID(int userID) {
        List<Order> orders = orderDao.getAllOrderWithUID(userID);
        List<OrderWithUser> ordersWithUser = new ArrayList<>();
        for (Order order : orders) {
            ordersWithUser.add(getOrderWithUserByOID(order.getId()));
        }
        return ordersWithUser;
    }

    public List<OrderWithUser> getAllOrders() {
        List<Order> orders = orderDao.getAllOrders();
        List<OrderWithUser> ordersWithUser = new ArrayList<>();
        for (Order order : orders) {
            ordersWithUser.add(getOrderWithUserByOID(order.getId()));
        }
        return ordersWithUser;
    }


    public int insertOrder(int userId, Integer discountId, double totalAmount) {
        return orderDao.insertOrder(userId, discountId, totalAmount);
    }

    public void insertOrderDetail(int orderId, int productId, int quantity, double price) {
        orderDao.insertOrderDetail(orderId, productId, quantity, price);
    }

    public void updateOrderStatus(int oid, String status) {
        orderDao.updateOrderStatus(oid, status);
    }

    public void updateBankID(int oid, int bid){
        orderDao.updateBankID(oid, bid);
    }

    public List<OrderWithUser> filterOrders(Integer orderId, String productName, String status) {
        List<Order> orders = orderDao.filterOrders(orderId, productName, status);
        List<OrderWithUser> ordersWithUser = new ArrayList<>();
        for (Order order : orders) {
            ordersWithUser.add(getOrderWithUserByOID(order.getId()));
        }
        return ordersWithUser;
    }


}
