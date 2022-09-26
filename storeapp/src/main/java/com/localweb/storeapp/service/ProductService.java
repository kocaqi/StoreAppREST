package com.localweb.storeapp.service;

import com.localweb.thelogin.thelogin.dao.ProductRepository;
import com.localweb.thelogin.thelogin.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product getProduct(int id) {
        return productRepository.getProductById(id);
    }

    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
