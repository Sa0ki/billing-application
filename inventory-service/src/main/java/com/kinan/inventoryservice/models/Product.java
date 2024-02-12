package com.kinan.inventoryservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Eren
 **/
@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
}
