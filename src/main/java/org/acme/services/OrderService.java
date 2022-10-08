package org.acme.services;

import org.acme.daos.OrderDao;
import org.acme.models.Order;
import org.acme.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {

    private OrderDao orderDao;

    public OrderService(){
        this.orderDao = new OrderDao();
    }

    public void addToCart(Product product, String clientId){
        Order order = this.orderDao.returnOrderByClient(clientId);
        if(order == null){
            order = new Order();
            UUID guid = UUID.randomUUID();
            List<Product> products = new ArrayList<>();
            products.add(product);
            order.setProducts(products);
            order.setClientId(clientId);
            order.setId(guid.toString());
            order.setTotalPrice(product.getPrice() * product.getQuantity());
            this.orderDao.insert(order);
        }else{
            order.getProducts().add(product);
            Double totalPrice = order.getTotalPrice() + (product.getPrice() * product.getQuantity());
            order.setTotalPrice(totalPrice);
            this.orderDao.update(order);
        }
    }

    public void updateQuantity(Product product, String clientId){
        Order order = this.orderDao.returnOrderByClient(clientId);
        order = recalculatePrice(product, order);
        this.orderDao.update(order);
    }

    public void removeFromCart(Product product, String clientId){
        Order order = this.orderDao.returnOrderByClient(clientId);
        List<Product> products = order.getProducts();
        products.remove(product);
        if (products != null && !products.isEmpty()) {
            order.setProducts(products);
            order.setTotalPrice(recalculatePrice(products));
            this.orderDao.update(order);
        }else{
            this.orderDao.delete(order);
        }
    }

    public Double recalculatePrice(List<Product> products){
        Double total = 0.0;
        if(products != null && !products.isEmpty()){
            for(Product product : products){
                total = total + (product.getPrice() * product.getQuantity());
            }
        }
        return total;
    }
    public Order recalculatePrice(Product product, Order order){
        List<Product> products = order.getProducts();
        if(products.contains(product)){
            products.remove(product);
            order.setTotalPrice(0.0);
            if(products != null && !products.isEmpty()) {
                order.setTotalPrice(recalculatePrice(products));
                order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * product.getQuantity()));
            }else{
                order.setTotalPrice(product.getPrice() * product.getQuantity());
            }
            products.add(product);
            order.setProducts(products);
        }
        return order;
    }

}
