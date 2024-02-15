package com.kinan.orderservice.repositories;

import com.kinan.orderservice.models.Order;
import com.kinan.orderservice.models.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Eren
 **/
@Repository
public interface IOrderRepository extends MongoRepository<Order, String> {
    Order findByStatusAndCustomerId(OrderStatus orderStatus, String customerId);
    List<Order> findByCustomerId(String customerId);
}
