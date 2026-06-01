package com.ecommerce.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {

        double total = 0;

        for (CartItem item : order.getItems()) {

            if (item.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }

            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            total += product.getPrice() * item.getQuantity();
        }

        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    @PostMapping("/checkout")
    public Order checkout(@RequestParam String userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        double total = 0;

        for (CartItem item : cart.getItems()) {

            if (item.getQuantity() <= 0) {
                throw new RuntimeException("Invalid quantity");
            }

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            total += product.getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setCustomerName(userId);
        order.setItems(cart.getItems());
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        cartRepository.delete(cart);

        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderRepository.deleteById(id);
        return "Order deleted successfully";
    }
}