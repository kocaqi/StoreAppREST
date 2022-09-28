package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.ProductDTO;
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
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO){
        productDTO.setDateCreated(LocalDate.now());
        productDTO.setDateUpdated(LocalDate.now());
        return new ResponseEntity<>(productService.create(productDTO), HttpStatus.CREATED);
    }

    //get all products
    @GetMapping
    public List<ProductDTO> getAll(){
        return productService.getAll();
    }

    //get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }
}
