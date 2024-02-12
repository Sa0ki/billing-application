
package com.kinan.customerservice.repositories;

import com.kinan.customerservice.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends MongoRepository<Customer, String> {
}