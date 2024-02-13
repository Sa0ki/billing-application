package com.kinan.billservice.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.kinan.billservice.mappers.OrderMapper;
import com.kinan.billservice.models.Bill;
import com.kinan.billservice.models.Customer;
import com.kinan.billservice.models.Order;
import com.kinan.billservice.models.ResponseMessage;
import com.kinan.billservice.repositories.IBillRepository;
import com.kinan.billservice.repositories.IOrderRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author Eren
 **/
@Service
public class BillService {
    private final IBillRepository billRepository;
    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;
    public BillService(IBillRepository billRepository, IOrderRepository orderRepository,
                       OrderMapper orderMapper){
        this.billRepository = billRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
    public ResponseEntity<Object> getBill(String orderId, Customer customer){
        // Check if the order exists.
        ResponseEntity<Object> response = this.orderRepository.getOrder(orderId);
        Order order = null;

        // If true, we retrieve the order, else we return the response with "Order not found".
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            order = this.orderMapper.convertToOrder(response.getBody());
            if(order.getId() == null) return response;
        }catch(Exception e){
            e.printStackTrace();
            return response;
        }

        // Check if the bill exists.
        if(this.billExists(orderId))
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage("The bill is already available to download."));

        // If false, we create a new one.
        Bill bill = new Bill();
        bill.setCustomerId(customer.getId());
        bill.setOrderId(orderId);
        bill.setDate(order.getDate());

        // We retrieve the customer information
        try{
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            PdfService.customerInformation(document, customer);
            PdfService.headerProductsTable(document);
            PdfService.bodyProductsTable(document, order);

            document.close();
            outputStream.close();

            bill = this.savePdfToDatabase(bill, outputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bill);
    }
    public byte[] downloadBill(String billId) {
        Bill bill = this.billRepository.findById(billId).orElse(null);
        return (bill == null) ? null: bill.getPdf();
    }
    private Boolean billExists(String orderId){
        return this.billRepository
                .findAll()
                .stream()
                .anyMatch(bill -> bill.getOrderId().equals(orderId));
    }
    private Bill savePdfToDatabase(Bill bill, ByteArrayOutputStream outputStream){
        try{
            byte[] pdfData = outputStream.toByteArray();
            bill.setPdf(pdfData);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return this.billRepository.save(bill);
    }
}
