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
    @Query("select p from OrderProduct p where p.order.id=?1 and p.product.id=?2")
    OrderProduct findByOrderAndProduct(long orderId, long productId);
}
