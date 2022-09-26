package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.id=?1")
    Product getProductById(int id);

}
