package org.acme.converters;

import com.google.gson.Gson;
import org.acme.models.Order;
import org.acme.models.Product;

public class ProductConverter {

    public static String productToJson(Product product){
        Gson gson = new Gson();
        String productString = gson.toJson(product);
        return productString;
    }

    public static Product jsonToProduct(String jsonString){
        Gson gson = new Gson();
        Product product = gson.fromJson(jsonString, Product.class);
        return product;
    }

}
