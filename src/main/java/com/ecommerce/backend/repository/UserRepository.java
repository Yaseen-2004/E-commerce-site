package com.ecommerce.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.backend.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    static User findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}