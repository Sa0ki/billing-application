package com.kinan.customerservice.models;

import lombok.*;

/**
 * @author Eren
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Product {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
}
