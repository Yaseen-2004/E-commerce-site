package com.ecommerce.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/add")
    public Cart addToCart(@RequestParam String userId, @RequestBody CartItem item) {

        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());

        if (cart.getUserId() == null) {
            cart.setUserId(userId);
            cart.setItems(new ArrayList<>());
        }

        List<CartItem> items = cart.getItems();

        boolean found = false;

        for (CartItem i : items) {
            if (i.getProductId().equals(item.getProductId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            items.add(item);
        }

        cart.setItems(items);

        return cartRepository.save(cart);
    }

    @GetMapping
    public Cart getCart(@RequestParam String userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    @DeleteMapping("/clear")
    public String clearCart(@RequestParam String userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart != null) {
            cartRepository.delete(cart);
        }

        return "Cart cleared";
    }
}