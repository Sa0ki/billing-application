package com.kinan.customerservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eren
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Order {
    private String id;
    private String customerId;
    private Map<String, Product> products = new HashMap<>();
    private LocalDate date = LocalDate.now();
    private OrderStatus status = OrderStatus.CREATED;
    private Double totalDue = 0.0;
}
