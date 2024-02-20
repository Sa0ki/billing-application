package com.kinan.keycloakservice.controllers;

import com.kinan.keycloakservice.models.Customer;
import com.kinan.keycloakservice.models.Order;
import com.kinan.keycloakservice.models.OrderProducts;
import com.kinan.keycloakservice.models.Product;
import com.kinan.keycloakservice.services.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Eren
 **/
@RestController
@RequestMapping("/auth/customers")
public class AuthController {
    private final Service service;
    public AuthController(Service service){
        this.service = service;
    }
    @PostMapping("/orders/get-orders")
    ResponseEntity<Object> getOrders(@RequestHeader HttpHeaders headers, @RequestParam String customerId){
        return this.service.getOrders(customerId);
    }
    @PostMapping("/orders/get-order")
    ResponseEntity<Object> getOrder(@RequestParam String orderId){
        return this.service.getOrder(orderId);
    }
    @PostMapping("/orders/place-order")
    ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                      @RequestParam Integer quantity){
        return this.service.placeOrder(customerId, productId, quantity);
    }
    @PostMapping("/orders/confirm-order")
    ResponseEntity<Object> confirmOrder(@RequestParam String orderId){
        return this.service.confirmOrder(orderId);
    }
    @PostMapping("/bills/get-bill")
    ResponseEntity<Object> getBill(@RequestParam String orderId, @RequestBody Customer customer){
        return this.service.getBill(orderId, customer);
    }
    @PostMapping("/bills/download-bill")
    ResponseEntity<Object> downloadBill(@RequestParam String billId){
        return this.service.downloadBill(billId);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password){
        return this.service.login(username, password);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Customer customer) {
        return this.service.register(customer);
    }
    @PostMapping("/get-customer")
    public ResponseEntity<Object> getCustomer(@RequestParam String email){
        return this.service.getCustomer(email);
    }
    @PostMapping("/orders/update-order")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderProducts orderProducts){
        return this.service.updateOrder(orderProducts);
    }
}
