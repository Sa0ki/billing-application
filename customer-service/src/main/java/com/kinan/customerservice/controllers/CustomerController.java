package com.kinan.customerservice.controllers;

import com.kinan.customerservice.models.Customer;
import com.kinan.customerservice.models.Order;
import com.kinan.customerservice.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/add")
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer){
        return this.customerService.addCustomer(customer);
    }
    @PostMapping("/orders/get-orders")
    public ResponseEntity<Object> getOrders(@RequestParam String customerId){
        return this.customerService.getOrders(customerId);
    }
    @PostMapping("/orders/get-order")
    public ResponseEntity<Object> getOrder(@RequestParam String orderId){
        return this.customerService.getOrder(orderId);
    }
    @PostMapping("/orders/place-order")
    public ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                             @RequestParam Integer quantity){
        return this.customerService.placeOrder(customerId, productId, quantity);
    }
    @PostMapping("/orders/confirm-order")
    ResponseEntity<Object> confirmOrder(@RequestParam String orderId){
        return this.customerService.confirmOrder(orderId);
    }
    @PostMapping("/bills/get-bill")
    public ResponseEntity<Object> getBill(@RequestParam String orderId, @RequestBody Customer customer){
        return this.customerService.getBill(orderId, customer);
    }
    @PostMapping("/bills/download-bill")
    public ResponseEntity<Object> downloadBill(@RequestParam String billId){
        return this.customerService.downloadBill(billId);
    }
    @PostMapping("/get-customer")
    public ResponseEntity<Object> getCustomer(@RequestParam String email){
        return this.customerService.getCustomer(email);
    }
    @PostMapping("/orders/update-order")
    ResponseEntity<Object> updateOrder(@RequestBody Order order){
        return this.customerService.updateOrder(order);
    }
}
