package com.kinan.keycloakservice.repositories;

import com.kinan.keycloakservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Eren
 **/
@FeignClient(name = "CUSTOMER-SERVICE")
public interface ICustomerRepository {
    @PostMapping("customers/add")
    ResponseEntity<Object> addCustomer(@RequestBody Customer customer);
    @PostMapping("customers/orders/get-orders")
    ResponseEntity<Object> getOrders(@RequestParam String customerId);
    @PostMapping("customers/orders/get-order")
    ResponseEntity<Object> getOrder(@RequestParam String orderId);
    @PostMapping("customers/orders/place-order")
    ResponseEntity<Object> placeOrder(@RequestParam String customerId, @RequestParam String productId,
                                             @RequestParam Integer quantity);
    @PostMapping("customers/orders/confirm-order")
    ResponseEntity<Object> confirmOrder(@RequestParam String orderId);
    @PostMapping("customers/bills/get-bill")
    ResponseEntity<Object> getBill(@RequestParam String orderId, @RequestBody Customer customer);
    @PostMapping("customers/bills/download-bill")
    ResponseEntity<Object> downloadBill(@RequestParam String billId);
}
