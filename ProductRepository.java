package com.ecommerce.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecommerce.backend.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategoryId(String categoryId);

}