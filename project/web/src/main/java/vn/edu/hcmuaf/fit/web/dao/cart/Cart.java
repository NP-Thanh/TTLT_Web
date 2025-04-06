package vn.edu.hcmuaf.fit.web.dao.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private int id;          // ID của giỏ hàng
    private int userId;
    Map<Integer, CartProduct> data= new HashMap<Integer, CartProduct>();

    public Cart() {}

    public Cart(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CartProduct> getList(){
        return new ArrayList<CartProduct>(data.values());
    }

    public Map<Integer, CartProduct> getItems() {
        return data;
    }
}

