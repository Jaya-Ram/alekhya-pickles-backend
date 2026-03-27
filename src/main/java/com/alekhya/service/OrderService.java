package com.alekhya.service;

import com.alekhya.dto.OrderResponse;
import com.alekhya.dto.PlaceOrderRequest;
import com.alekhya.dto.ShippingAddressDto;
import com.alekhya.model.*;
import com.alekhya.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepo;

    private User getCurrentUser(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByUserName(userName)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request){
        // Get Logged-in User
        User user = getCurrentUser();

        // Fetch Cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()-> new RuntimeException("Cart not found"));

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is Empty");
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentMethod(request.getPaymentMethod());

        // Shipping Address
        ShippingAddressDto addr = new ShippingAddressDto();
        order.setFullName(addr.getFullName());
        order.setMobile(addr.getMobile());
        order.setAddressLine(addr.getAddressLine());
        order.setCity(addr.getCity());
        order.setState(addr.getState());
        order.setPincode(addr.getPincode());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for(CartItems cartItems : cart.getItems()){
            OrderItem item = new OrderItem();
            item.setProduct(cartItems.getProduct());
            item.setPrice(cartItems.getPrice());
            item.setQuantity(cartItems.getQuantity());

            item.setOrder(order);
            orderItems.add(item);

            total += cartItems.getPrice() * cartItems.getQuantity();
        }

        // Set values
        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        // Save to db
        orderRepository.save(order);

        // clear cart
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        // return response

        return new OrderResponse(
                order.getId(), order.getTotalAmount(), order.getStatus().name()
        );
    }

}