package com.kinan.customerservice.repositories;

import com.kinan.customerservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Eren
 **/
@FeignClient(name = "BILL-SERVICE")
public interface IBillRepository {
    @PostMapping("bills/get-bill")
    ResponseEntity<Object> getBill(@RequestParam String orderId, @RequestBody Customer customer);
    @PostMapping("bills/download-bill")
    ResponseEntity<Object> downloadBill(@RequestParam String billId);
}
