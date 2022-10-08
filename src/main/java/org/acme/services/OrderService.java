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
            order.setTotalPrice(product.getPrice());
            this.orderDao.insert(order);
        }else{
            order.getProducts().add(product);
            Double totalPrice = order.getTotalPrice() + product.getPrice();
            order.setTotalPrice(totalPrice);
            this.orderDao.update(order);
        }
    }

}
