package com.ecommerce.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecommerce.backend.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
}