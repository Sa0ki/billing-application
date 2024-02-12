package com.kinan.orderservice.repositories;

import com.kinan.orderservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Eren
 **/
@FeignClient(name = "INVENTORY-SERVICE")
public interface IProductRepository {
    @GetMapping("products/get-product")
    Product getProductById(@RequestParam String productId);
}
