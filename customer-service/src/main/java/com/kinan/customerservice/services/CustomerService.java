package com.kinan.customerservice.services;

import com.kinan.customerservice.repositories.ICustomerRepository;
import com.kinan.customerservice.repositories.IOrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Eren
 **/
@Service
public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final IOrderRepository orderRepository;
    public CustomerService(ICustomerRepository customerRepository, IOrderRepository orderRepository){
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }
    public ResponseEntity<Object> placeOrder(String customerId, String productId, Integer quantity){
        return this.orderRepository.placeOrder(customerId, productId, quantity);
    }
}
