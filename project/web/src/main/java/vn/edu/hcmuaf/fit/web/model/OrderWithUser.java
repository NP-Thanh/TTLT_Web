package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;
import java.util.List;

public class OrderWithUser implements Serializable {
    private Order order;
    private List<OrderDetail> orderDetail;
    private User user;
    private List<Product> product;

    public OrderWithUser() {
    }

    public OrderWithUser(Order order, List<OrderDetail> orderDetail, User user, List<Product> product) {
        this.order = order;
        this.orderDetail = orderDetail;
        this.user = user;
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
