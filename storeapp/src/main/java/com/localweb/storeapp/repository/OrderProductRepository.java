package com.localweb.storeapp.repository;

import com.localweb.thelogin.thelogin.entities.Order;
import com.localweb.thelogin.thelogin.entities.OrderProduct;
import com.localweb.thelogin.thelogin.entities.OrderProductKey;
import com.localweb.thelogin.thelogin.entities.Product;
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
