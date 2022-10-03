package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.entityDTO.ProductDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //create product
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO){
        productDTO.setDateCreated(LocalDate.now());
        productDTO.setDateUpdated(LocalDate.now());
        return new ResponseEntity<>(productService.create(productDTO), HttpStatus.CREATED);
    }

    //get all products
    @GetMapping
    public Response<ProductDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return productService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    //get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    //update product
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO productDTO, @PathVariable(name = "id") int id){
        ProductDTO productResponse = productService.update(productDTO, id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
