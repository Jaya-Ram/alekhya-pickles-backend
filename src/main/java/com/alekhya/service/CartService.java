package com.alekhya.service;

import com.alekhya.dto.AddToCartRequest;
import com.alekhya.dto.CartResponse;
import com.alekhya.dto.UpdateCartRequest;

public interface CartService {
    CartResponse addToCart(AddToCartRequest request);
    CartResponse getCart();
    CartResponse updateCart(UpdateCartRequest request);
    void removeItem(Long productId);
    void clearCart();
}
