package com.kinan.billservice.controllers;

import com.kinan.billservice.models.Customer;
import com.kinan.billservice.services.BillService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eren
 **/
@RestController
@RequestMapping("bills")
public class BillController {
    private final BillService billService;
    public BillController(BillService billService){
        this.billService = billService;
    }
    @PostMapping("get-bill")
    public ResponseEntity<Object> getBill(@RequestParam String orderId, @RequestBody Customer customer){
        return this.billService.getBill(orderId, customer);
    }
    @PostMapping("download-bill")
    public byte[] downloadBill(@RequestParam String billId){
        return this.billService.downloadBill(billId);
    }
}
