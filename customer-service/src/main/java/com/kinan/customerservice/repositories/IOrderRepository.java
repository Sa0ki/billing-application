package com.kinan.customerservice.repositories;

import com.kinan.customerservice.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Eren
 **/
@FeignClient(name = "ORDER-SERVICE")
public interface IOrderRepository {
    @PostMapping(value = "orders/place-order")
    ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                             @RequestParam Integer quantity);
    @PostMapping("orders/confirm-order")
    ResponseEntity<Object> confirmOrder(@RequestParam String orderId);
    @PostMapping("orders/get-orders")
    ResponseEntity<Object> getOrders(@RequestParam String customerId);
    @PostMapping("orders/get-order")
    ResponseEntity<Object> getOrder(@RequestParam String orderId);
    @PostMapping("orders/update-order")
    ResponseEntity<Object> updateOrder(@RequestBody Order order);
}
