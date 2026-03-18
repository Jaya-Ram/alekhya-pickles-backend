package com.alekhya.service;

import com.alekhya.model.Product;
import com.alekhya.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService{
    @Autowired
    private ProductRepository productRepository;

    // Adding product
    public Object createProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return productRepository.save(product);
    }

    // Retrieving all products
    @Transactional
    public List<Product> getAllProducts(int pageNo, int pageSize, Sort sort, String search) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        if (search == null || search.trim().isEmpty()) {
            return productRepository.findAll(pageable).getContent();
        } else {
            return productRepository.findByNameOrDescriptionContainingIgnoreCase(search, search, pageable);
        }
    }

    // Retrieving products by id
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found with id: "+id)
        );
    }
    // updating product
    public Product updateProduct(Long id, Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = productRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Product not found with id: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setCreatedAt(product.getCreatedAt());

        if(imageFile != null && !imageFile.isEmpty()){
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
            existingProduct.setImageData(imageFile.getBytes());
        }
        return productRepository.save(existingProduct);

    }

    // deleting product by id
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found with id: " + id));

        productRepository.deleteById(id);
    }
}
