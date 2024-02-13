package com.kinan.billservice.repositories;

import com.kinan.billservice.models.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Eren
 **/
@Repository
public interface IBillRepository extends MongoRepository<Bill, String> {
}
