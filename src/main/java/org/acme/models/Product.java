package org.acme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private Integer quantity;
    private Double price;
    private String code;
}
