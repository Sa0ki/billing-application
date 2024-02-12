package com.kinan.inventoryservice.services;

import com.kinan.inventoryservice.models.Product;

import java.util.List;

/**
 * @author Eren
 **/
public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(String id);
}
