package com.food.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.server.models.CartItem;
import com.food.server.models.User;
import com.food.server.repository.UserRepository;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    public void addToCart(String userId, Integer productId) {
    userRepository.findById(userId).ifPresent(user -> {
        if (user.getCart() == null) {
            user.setCart(new ArrayList<>());
        }

        // ✅ check if product already in cart
        boolean exists = false;
        for (CartItem item : user.getCart()) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1); // increase quantity
                exists = true;
                break;
            }
        }

        // ✅ if new product, add it
        if (!exists) {
            user.getCart().add(new CartItem(productId, 1));
        }

        userRepository.save(user);
    });
}
    public List<CartItem> getCartItems(String userId) {
        return userRepository.findById(userId)
                .map(User::getCart)
                .orElse(new ArrayList<>());
    }


    public void removeFromCart(String userId, Integer productId) {
        
        userRepository.findById(userId).ifPresent(user -> {
            user.getCart().removeIf(item -> item.getProductId().equals(productId));
            userRepository.save(user);
        });
    }

    public void increaseQuantity(String userId, Integer productId) {
        
        userRepository.findById(userId).ifPresent(user -> {
            for (CartItem item : user.getCart()) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + 1); // increase quantity
                    break;
                }
            }
            userRepository.save(user);
        });
    }
    public void decreaseQuantity(String userId, Integer productId) {
        
        userRepository.findById(userId).ifPresent(user -> {
            for (CartItem item : user.getCart()) {
                if (item.getProductId().equals(productId) && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1); // decrease quantity
                    break;
                }
            }
            userRepository.save(user);
        });
    }

    public int totalItems(String userId) {
        return userRepository.findById(userId)
                .map(user -> user.getCart().size())
                .orElse(0);
    }


}
