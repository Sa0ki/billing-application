package com.kinan.inventoryservice.repositories;

import com.kinan.inventoryservice.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Eren
 **/
@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
}
