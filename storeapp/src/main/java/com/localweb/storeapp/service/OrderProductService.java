package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {

    @Autowired
    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public List<OrderProduct> findProductsByOrder(Order order) {
        return orderProductRepository.findProductsByOrder(order);
    }

    public void save(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }
}
