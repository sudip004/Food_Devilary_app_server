package com.food.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.server.models.CartItem;

import com.food.server.service.ProductService;

@RestController
@RequestMapping("/api")

public class ProductController extends BaseClass{

    @Autowired
    public ProductService productService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> data) {
        String userId = (String) data.get("userId");
        Integer productId = (Integer) data.get("productId");
        productService.addToCart(userId, productId);
        
        return ResponseEntity.ok("Product added to cart");
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestBody Map<String, Object> data) {
        String userId = (String) data.get("userId");
        Integer productId = (Integer) data.get("productId");
        productService.removeFromCart(userId, productId);
        
        return ResponseEntity.ok("Product removed from cart");
    }

    @PostMapping("/cart-items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestBody Map<String, Object> data) {
       List<CartItem> cartItems = productService.getCartItems(data.get("userId").toString());
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/increase-quantity")
    public String increaseQuantity(@RequestBody Map<String, Object> data) {
        // Implementation for increasing quantity
        String userId = (String) data.get("userId");
        Integer productId = (Integer) data.get("productId");
        productService.increaseQuantity(userId, productId);
        return "Quantity increased";
    }

    @PostMapping("/decrease-quantity")
    public String decreaseQuantity(@RequestBody Map<String, Object> data) {
        // Implementation for decreasing quantity
        String userId = (String) data.get("userId");
        Integer productId = (Integer) data.get("productId");
        productService.decreaseQuantity(userId, productId);
        return "Quantity increased";

    }

    @PostMapping("/total-items")
    public int totalItems(@RequestBody Map<String, Object> data) {
        String userId = (String) data.get("userId");
        int total = productService.totalItems(userId);
        return total;
    }


   

}
