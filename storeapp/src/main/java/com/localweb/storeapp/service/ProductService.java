package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.ProductDTO;
import com.localweb.storeapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        //convert DTO to entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDateCreated(productDTO.getDateCreated());
        product.setDateUpdated(productDTO.getDateUpdated());
        product.setOrders(productDTO.getOrders());

        Product newProduct = productRepository.save(product);

        //convert entity to DTO
        ProductDTO productResponse = new ProductDTO();
        productResponse.setId(newProduct.getId());
        productResponse.setName(newProduct.getName());
        productResponse.setPrice(newProduct.getPrice());
        productResponse.setStock(newProduct.getStock());
        productResponse.setOrders(newProduct.getOrders());
        productResponse.setDateCreated(newProduct.getDateCreated());
        productResponse.setDateUpdated(newProduct.getDateUpdated());

        return productResponse;
    }
}
