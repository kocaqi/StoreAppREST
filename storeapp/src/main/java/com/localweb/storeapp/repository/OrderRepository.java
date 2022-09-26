package com.localweb.storeapp.repository;

import com.localweb.thelogin.thelogin.entities.Order;
import com.localweb.thelogin.thelogin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.id = ?1")
    Order findOrderById(int id);

    List<Order> findAllByUser(User user);
}
