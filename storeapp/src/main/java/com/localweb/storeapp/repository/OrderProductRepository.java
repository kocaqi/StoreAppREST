package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.OrderProductKey;
import com.localweb.storeapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository  extends JpaRepository<OrderProduct, OrderProductKey> {

    @Query("select o from OrderProduct o where o.order=?1")
    List<OrderProduct> findProductsByOrder(Order order);

    @Query("select o from OrderProduct o where o.order=?1 and o.product=?2")
    OrderProduct getByOrderAndProduct(Order order, Product product);
}
