package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.ProductDTO;
import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //create product
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createUser(@RequestBody ProductDTO productDTO){
        productDTO.setDateCreated(LocalDate.now());
        productDTO.setDateUpdated(LocalDate.now());
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    //get all products
    @GetMapping
    public List<ProductDTO> getAllUsers(){
        return productService.getAllProducts();
    }
}
