package com.kinan.orderservice.controllers;

import com.kinan.orderservice.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/get-order")
    public ResponseEntity<Object> getOrder(@RequestParam String orderId){
        return this.orderService.getOrder(orderId);
    }
}
