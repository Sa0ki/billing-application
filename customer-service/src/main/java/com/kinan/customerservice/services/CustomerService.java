package com.kinan.customerservice.services;

import com.kinan.customerservice.models.Customer;
import com.kinan.customerservice.models.Order;
import com.kinan.customerservice.models.ResponseMessage;
import com.kinan.customerservice.repositories.IBillRepository;
import com.kinan.customerservice.repositories.ICustomerRepository;
import com.kinan.customerservice.repositories.IOrderRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<Object> addCustomer(Customer customer){
        if(this.customerRepository.findByEmail(customer.getEmail()) != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.customerRepository.save(customer));
    }
    public ResponseEntity<Object> getOrders(String customerId){
        return this.orderRepository.getOrders(customerId);
    }
    public ResponseEntity<Object> getOrder(String orderId){
        return this.orderRepository.getOrder(orderId);
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
        byte[] pdfBytes =  this.billRepository.downloadBill(billId);
        if(pdfBytes == null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage("Bill not found."));

        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture.pdf");
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    public ResponseEntity<Object> getCustomer(String email){
        Customer customer = this.customerRepository.findByEmail(email);
        if(customer != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customer);
        return null;
    }
    public ResponseEntity<Object> updateOrder(Order order){
        return this.orderRepository.updateOrder(order);
    }
}
