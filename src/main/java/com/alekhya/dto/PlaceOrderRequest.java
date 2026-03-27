package com.alekhya.dto;

public class PlaceOrderRequest {

    private String paymentMethod;

    private ShippingAddressDto shippingAddress;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}