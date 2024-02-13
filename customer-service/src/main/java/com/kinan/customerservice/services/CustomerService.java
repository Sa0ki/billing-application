package com.kinan.customerservice.services;

import com.kinan.customerservice.models.Customer;
import com.kinan.customerservice.repositories.IBillRepository;
import com.kinan.customerservice.repositories.ICustomerRepository;
import com.kinan.customerservice.repositories.IOrderRepository;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Eren
 **/
@Service
public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final IOrderRepository orderRepository;
    private final IBillRepository billRepository;
    public CustomerService(ICustomerRepository customerRepository, IOrderRepository orderRepository,
                           IBillRepository billRepository){
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.billRepository = billRepository;
    }
    public ResponseEntity<Object> placeOrder(String customerId, String productId, Integer quantity){
        return this.orderRepository.placeOrder(customerId, productId, quantity);
    }
    public ResponseEntity<Object> confirmOrder(String orderId){
        return this.orderRepository.confirmOrder(orderId);
    }
    public ResponseEntity<Object> getBill(String orderId, Customer customer){
        return this.billRepository.getBill(orderId, customer);
    }

    public ResponseEntity<Object> downloadBill(String billId){
        ResponseEntity<Object> response =  this.billRepository.downloadBill(billId);
        System.out.println(response);
        return response;
    }
}
