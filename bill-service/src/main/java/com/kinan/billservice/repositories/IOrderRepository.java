package com.kinan.billservice.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Eren
 **/
@FeignClient(name = "ORDER-SERVICE")
public interface IOrderRepository {
    @PostMapping("orders/get-order")
    public ResponseEntity<Object> getOrder(@RequestParam String orderId);
}
