package org.acme.converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.acme.models.Order;
import org.acme.models.Product;

public class OrderConverter {

    public static String orderToJson(Order order){
        Gson gson = new Gson();
        /*if(order.getProducts() != null && !order.getProducts().isEmpty()){
            JsonArray productsJsonArray = new JsonArray();
            for (Product product: order.getProducts()) {
                productsJsonArray.add(ProductConverter.productToJson(product));
            }
        }*/
        String orderString = gson.toJson(order);
        return orderString;
    }

    public static Order jsonToOrder(String jsonString){
        Gson gson = new Gson();
        Order order = gson.fromJson(jsonString, Order.class);
        return order;
    }
}
