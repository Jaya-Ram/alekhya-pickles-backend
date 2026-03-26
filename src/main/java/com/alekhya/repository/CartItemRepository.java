package com.alekhya.repository;

import com.alekhya.model.Cart;
import com.alekhya.model.CartItems;
import com.alekhya.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);

}
