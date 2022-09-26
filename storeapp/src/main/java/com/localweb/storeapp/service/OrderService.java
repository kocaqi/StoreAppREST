package com.localweb.storeapp.service;

import com.localweb.thelogin.thelogin.dao.OrderRepository;
import com.localweb.thelogin.thelogin.entities.Order;
import com.localweb.thelogin.thelogin.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order getOrder(int id) {
        return orderRepository.findOrderById(id);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }
}
