package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.ProductDTO;
import com.localweb.storeapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO create(ProductDTO productDTO){
        //convert DTO to entity
        Product product = maptoEntity(productDTO);
        Product newProduct = productRepository.save(product);

        //convert entity to DTO
        ProductDTO productResponse = mapToDTO(newProduct);

        return productResponse;
    }

    public List<ProductDTO> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
    }

    private ProductDTO mapToDTO(Product newProduct){
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

    private Product maptoEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDateCreated(productDTO.getDateCreated());
        product.setDateUpdated(productDTO.getDateUpdated());
        product.setOrders(productDTO.getOrders());
        return product;
    }

    public ProductDTO getById(int id) {
        Product product = productRepository.findProductById(id);
        ProductDTO productDTO = mapToDTO(product);
        return productDTO;
    }
}
