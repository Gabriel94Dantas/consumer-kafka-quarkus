package org.acme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private String id;
    private List<Product> products;
    private String clientId;
    private Double totalPrice;

}
