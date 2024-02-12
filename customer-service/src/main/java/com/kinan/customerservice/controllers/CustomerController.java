package com.kinan.customerservice.controllers;

import com.kinan.customerservice.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eren
 **/
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @PostMapping("/orders/place-order")
    public ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                             @RequestParam Integer quantity){
        return this.customerService.placeOrder(customerId, productId, quantity);
    }
}
