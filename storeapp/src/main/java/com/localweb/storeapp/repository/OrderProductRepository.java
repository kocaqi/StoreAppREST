package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository  extends JpaRepository<OrderProduct, OrderProductKey>, JpaSpecificationExecutor<OrderProduct> {
    @Query("select p from OrderProduct p where p.order.id=?1 and p.product.id=?2")
    OrderProduct findByOrderAndProduct(long orderId, long productId);
}
