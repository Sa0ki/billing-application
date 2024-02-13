package com.kinan.orderservice.services;

import com.kinan.orderservice.models.Order;
import com.kinan.orderservice.models.OrderStatus;
import com.kinan.orderservice.models.Product;
import com.kinan.orderservice.models.ResponseMessage;
import com.kinan.orderservice.repositories.IOrderRepository;
import com.kinan.orderservice.repositories.IProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eren
 **/
@Service
public class OrderService {
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    public OrderService(IOrderRepository orderRepository, IProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    public ResponseEntity<Object> placeOrder(String customerId, String productId, Integer quantity){
        // Search the chosen product.
        Product product = productExists(productId);
        if(product == null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Product not found."));

        // Update the product with the actual quantity wanted by the customer.
        product.setQuantity(quantity);

        // Check if a created order already exists.
        // If true, we add the product to the current order.
        // If false, we create a new order and add the product to it.
        Order order = this.orderRepository.findByStatusAndCustomerId(OrderStatus.CREATED, customerId);
        if(order != null){
            if(order.getProducts().containsKey(productId))
                this.updateQuantityOfProductIfExists(product, order);
            else
                order.getProducts().put(productId, product);
            order.setTotalDue(order.getTotalDue() + (product.getPrice() * quantity));
        }
        else{
            Map<String, Product> productMap = new HashMap<>();
            productMap.put(productId, product);

            order = new Order();
            order.setCustomerId(customerId);
            order.setProducts(productMap);
            order.setTotalDue(product.getPrice() * quantity);
        }

        // Save or update the order.
        order = this.orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    public ResponseEntity<Object> confirmOrder(String orderId){
        // Check if the order exists.
        Order order = this.orderRepository.findById(orderId).orElse(null);

        // If false, we return a message.
        if(order == null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Order not found."));

        // Check if the order has already been confirmed.
        if(order.getStatus().equals(OrderStatus.CONFIRMED))
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage("Order is already confirmed."));

        // Else, we change its status to CONFIRMED.
        order.setStatus(OrderStatus.CONFIRMED);
        order = this.orderRepository.save(order);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(order);
    }
    public ResponseEntity<Object> getOrder(String orderId){
        Order order = this.orderRepository.findById(orderId).orElse(null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Objects.requireNonNullElseGet(order, () -> new ResponseMessage("Order not found.")));
    }
    private Product productExists(String productId){
        return this.productRepository.getProductById(productId);
    }
    private void updateQuantityOfProductIfExists(Product product, Order order){
        Integer currentQuantity = order.getProducts().get(product.getId()).getQuantity();
        order.getProducts().get(product.getId()).setQuantity(currentQuantity + product.getQuantity());
    }
}
