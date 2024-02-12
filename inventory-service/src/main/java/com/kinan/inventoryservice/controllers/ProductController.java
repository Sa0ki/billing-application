package com.kinan.inventoryservice.controllers;

import com.kinan.inventoryservice.models.Product;
import com.kinan.inventoryservice.services.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Eren
 **/
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productService;
    public ProductController(ProductServiceImpl productService){
        this.productService = productService;
    }
    @GetMapping("/get-all-products")
    public List<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }
    @GetMapping("/get-product")
    public Product getProductById(@RequestParam String productId){
        return this.productService.getProductById(productId);
    }
}
