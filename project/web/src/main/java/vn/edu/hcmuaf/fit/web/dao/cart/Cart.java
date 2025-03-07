package vn.edu.hcmuaf.fit.web.dao.cart;

import vn.edu.hcmuaf.fit.web.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart {
    Map<Integer, CartProduct> data= new HashMap<Integer, CartProduct>();

    public boolean add(Product p){
        if (data.containsKey(p.getId())){
            update(p.getId(), data.get(p.getId()).getQuantity()+1);
            return true;
        }
        data.put(p.getId(), convert(p));
        return true;
    }

    public boolean sub(Product p){
        return update(p.getId(), data.get(p.getId()).getQuantity()-1);
    }

    public boolean update(int id, int quantity){
        if (!data.containsKey(id) || quantity < 1){
            return false;
        }
        CartProduct cartProduct = data.get(id);
        cartProduct.setQuantity(quantity);
        data.put(id, cartProduct);
        return true;
    }

    public boolean remove(int id){
        return data.remove(id) != null;
    }

    public List<CartProduct> getList(){
        return new ArrayList<CartProduct>(data.values());
    }

    public int getTotalQuantity(){
        AtomicInteger i = new AtomicInteger(0);
        data.forEach((k, v)-> i.getAndAdd(v.getQuantity()));
        return i.get();
    }

    public Double getTotal(){
        AtomicReference<Double> d= new AtomicReference<>(0.0);
        data.values().forEach(cp -> d.updateAndGet(v -> (double) (v + (cp.getQuantity()*cp.getPrice()))));
        return d.get();
    }

    private CartProduct convert(Product p){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(p.getId());
        cartProduct.setProductName(p.getName());
        cartProduct.setPrice(p.getPrice());
        cartProduct.setQuantity(1);
        cartProduct.setDuration(p.getDuration());
        cartProduct.setImage(p.getImage());
        return cartProduct;
    }
}
