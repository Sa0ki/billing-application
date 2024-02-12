package com.kinan.inventoryservice.services;

import com.kinan.inventoryservice.models.Product;
import com.kinan.inventoryservice.repositories.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eren
 **/
@Service
public class ProductServiceImpl implements IProductService{
    private final IProductRepository productRepository;
    public ProductServiceImpl(IProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
    @Override
    public Product getProductById(String id){
        return this.productRepository.findById(id).orElse(null);
    }
}
