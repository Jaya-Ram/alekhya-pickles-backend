package com.alekhya.controller;

import com.alekhya.dto.AddToCartRequest;
import com.alekhya.dto.CartResponse;
import com.alekhya.dto.UpdateCartRequest;
import com.alekhya.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceImpl cartService;

    // Add to Cart
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddToCartRequest request){
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    // Update Cart
    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateCart(@RequestBody UpdateCartRequest request){
        return ResponseEntity.ok(cartService.updateCart(request));
    }

    // Get Cart
    @GetMapping
    public ResponseEntity<CartResponse> getCart(){
        return ResponseEntity.ok(cartService.getCart());
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeItem(@PathVariable Long productId){
        cartService.removeItem(productId);
        return ResponseEntity.ok("Item removed Successfully");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(){
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared Successfully");
    }

}
