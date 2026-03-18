package com.alekhya.repository;

import com.alekhya.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable
    );

}

