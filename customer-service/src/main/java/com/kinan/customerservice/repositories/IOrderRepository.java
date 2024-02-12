package com.kinan.customerservice.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Eren
 **/
@FeignClient(name = "ORDER-SERVICE")
public interface IOrderRepository {
    @PostMapping(value = "orders/place-order")
    public ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                             @RequestParam Integer quantity);
}
