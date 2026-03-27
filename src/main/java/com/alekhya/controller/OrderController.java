package com.alekhya.controller;

import com.alekhya.dto.OrderResponse;
import com.alekhya.dto.PlaceOrderRequest;
import com.alekhya.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody PlaceOrderRequest request) {

        return ResponseEntity.ok(orderService.placeOrder(request));
    }
}