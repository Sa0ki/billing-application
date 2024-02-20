package com.kinan.keycloakservice.models;

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
public class OrderProducts {
    private Order order;
    private Product[] products;
}
