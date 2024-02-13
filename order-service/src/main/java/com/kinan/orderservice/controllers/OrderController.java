package com.kinan.orderservice.controllers;

import com.kinan.orderservice.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eren
 **/
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @PostMapping("/place-order")
    public ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                             @RequestParam Integer quantity){
        return this.orderService.placeOrder(customerId, productId, quantity);
    }
    @PostMapping("/confirm-order")
    public ResponseEntity<Object> confirmOrder(@RequestParam String orderId){
        return this.orderService.confirmOrder(orderId);
    }
}
