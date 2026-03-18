package com.alekhya.controller;

import com.alekhya.model.Product;
import com.alekhya.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController{
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile
    ){

        try{
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);
            return new ResponseEntity<>(
                    productService.createProduct(product, imageFile), HttpStatus.CREATED);
        }
        catch(IOException e){
            return new ResponseEntity<>(
                    "Error while saving product data: "+ e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "6", required = false) int pageSize,
            @RequestParam(defaultValue = "productId", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sortDir,
            @RequestParam(required = false) String search
    ){
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("ASC")){
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }
        return new ResponseEntity<>(productService.getAllProducts(pageNo-1, pageSize, sort, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Product Not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ){

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);

            Product updateProduct = productService.updateProduct(id, product, imageFile);
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error while updating product: " +e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        try{
            productService.deleteProductById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error while deleting the Product " +e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
