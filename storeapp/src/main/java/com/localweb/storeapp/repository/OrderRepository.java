package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    Order findOrderById(long id);
}
