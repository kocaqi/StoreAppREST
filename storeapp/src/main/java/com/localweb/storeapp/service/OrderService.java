package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.payload.OrderDTO;
import com.localweb.storeapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO create(OrderDTO orderDTO){
        //convert DTO to entity
        Order order = mapToEntity(orderDTO);
        Order newOrder = orderRepository.save(order);

        //convert entity to DTO
        OrderDTO orderRespose = mapToDTO(newOrder);

        return orderRespose;
    }

    public List<OrderDTO> getAll(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> mapToDTO(order)).collect(Collectors.toList());
    }

    private OrderDTO mapToDTO(Order newOrder){
        OrderDTO orderRespose = new OrderDTO();
        orderRespose.setId(newOrder.getId());
        orderRespose.setUser(newOrder.getUser());
        orderRespose.setOrderProducts(newOrder.getOrderProducts());
        orderRespose.setAmount(newOrder.getAmount());
        orderRespose.setClient_id(newOrder.getClient_id());
        orderRespose.setDateCreated(newOrder.getDateCreated());
        orderRespose.setDateUpdated(newOrder.getDateUpdated());
        return orderRespose;
    }

    private Order mapToEntity(OrderDTO orderDTO){
        Order order = new Order();
        order.setUser(orderDTO.getUser());
        order.setOrderProducts(orderDTO.getOrderProducts());
        order.setAmount(orderDTO.getAmount());
        order.setClient_id(orderDTO.getClient_id());
        order.setDateCreated(orderDTO.getDateCreated());
        order.setDateUpdated(orderDTO.getDateUpdated());
        return order;
    }

    public OrderDTO getById(int id) {
        Order order = orderRepository.findOrderById(id);
        return mapToDTO(order);
    }
}
