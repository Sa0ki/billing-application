package com.kinan.customerservice.services;

import com.kinan.customerservice.models.Customer;
import com.kinan.customerservice.models.ResponseMessage;
import com.kinan.customerservice.repositories.IBillRepository;
import com.kinan.customerservice.repositories.ICustomerRepository;
import com.kinan.customerservice.repositories.IOrderRepository;
import feign.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
}
