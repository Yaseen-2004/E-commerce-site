package com.ecommerce.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecommerce.backend.model.Cart;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserId(String userId);

}