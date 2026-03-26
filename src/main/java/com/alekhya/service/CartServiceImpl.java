package com.alekhya.service;

import com.alekhya.dto.AddToCartRequest;
import com.alekhya.dto.CartItemResponse;
import com.alekhya.dto.CartResponse;
import com.alekhya.dto.UpdateCartRequest;
import com.alekhya.model.Cart;
import com.alekhya.model.CartItems;
import com.alekhya.model.Product;
import com.alekhya.model.User;
import com.alekhya.repository.CartItemRepository;
import com.alekhya.repository.CartRepository;
import com.alekhya.repository.ProductRepository;
import com.alekhya.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    private User getCurrentUser(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByUserName(userName)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    private Cart getOrCreateCart(User user){
        return cartRepo.findByUser(user)
                .orElseGet(()->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                });
    }

    // calculate the total
    private void updateTotal(Cart cart){
        double price = cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        cart.setTotalPrice(price);
        cartRepo.save(cart);
    }

    // Entity (Cart) to DTO (CartResponse) Conversion
    private CartResponse mapToResponse(Cart cart){
        List<CartItemResponse> items = cart.getItems().stream().map(item -> {
            CartItemResponse dto = mapper.map(item, CartItemResponse.class);

            dto.setProductId(item.getProduct().getProductId());
            dto.setProductName(item.getProduct().getName());
            dto.setTotalPrice(item.getPrice() * item.getQuantity());
            return dto;
        }).toList();
        CartResponse response = new CartResponse();
        response.setItems(items);
        response.setTotalPrice(cart.getTotalPrice());

        return response;
    }

    // Add to Cart

    @Override
    public CartResponse addToCart(AddToCartRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        CartItems item = cartItemRepo.findByCartAndProduct(cart, product)
                .orElse(null);

        if(item != null){
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }
        else{
            item = new CartItems();
            item.setCart(cart);
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setQuantity(request.getQuantity());

            cart.getItems().add(item);
        }

        cartItemRepo.save(item);
        updateTotal(cart);
        cartRepo.save(cart);

        return mapToResponse(cart);
    }

    // Get Entire Cart
    @Override
    public CartResponse getCart() {

        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        return mapToResponse(cart);
    }

    // Update Cart
    @Override
    public CartResponse updateCart(UpdateCartRequest request) {

        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));
        CartItems item = cartItemRepo.findByCartAndProduct(cart, product)
                .orElseThrow(()-> new RuntimeException("Item not found"));

        if(request.getQuantity() <= 0){
            cart.getItems().remove(item);
            cartItemRepo.delete(item);
        }
        else{
            item.setQuantity(request.getQuantity());
            cartItemRepo.save(item);
        }

        updateTotal(cart);
        cartRepo.save(cart);

        return mapToResponse(cart);
    }

    // Remove Item
    @Override
    public void removeItem(Long productId) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        CartItems item = cartItemRepo.findByCartAndProduct(cart, product)
                .orElseThrow(()-> new RuntimeException("Item not found"));

        cart.getItems().remove(item);
        cartItemRepo.delete(item);

        updateTotal(cart);
        cartRepo.save(cart);
    }

    // Clear Cart
    @Override
    public void clearCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        cart.getItems().clear();
        cart.setTotalPrice(0.0);

        cartRepo.save(cart);
    }
}