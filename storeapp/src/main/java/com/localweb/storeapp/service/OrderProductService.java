package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.payload.entityDTO.OrderProductDTO;
import com.localweb.storeapp.repository.OrderProductRepository;
import com.localweb.storeapp.repository.OrderRepository;
import com.localweb.storeapp.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.DTD;
import java.time.LocalDate;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public OrderProductDTO create(OrderProductDTO orderProductDTO, int orderId){
        OrderProduct orderProduct = mapToEntity(orderProductDTO);
        orderProduct.setDateCreated(LocalDate.now());
        orderProduct.setDateUpdated(LocalDate.now());
        orderProduct.setAmount(orderProductDTO.getQuantity() * orderProductDTO.getProduct().getPrice());
        Order order = orderRepository.findOrderById(orderId);
        orderProduct.setOrder(order);
        OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);

        return mapToDTO(newOrderProduct);
    }

    private OrderProductDTO mapToDTO(OrderProduct newOrderProduct){
        return modelMapper.map(newOrderProduct, OrderProductDTO.class);
    }

    private OrderProduct mapToEntity(OrderProductDTO orderProductDTO){
        return modelMapper.map(orderProductDTO, OrderProduct.class);
    }

    public void delete(int orderId, int productId) {
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(orderId, productId);
        orderProductRepository.delete(orderProduct);
    }

    public OrderProduct findByOrderAndProduct(long orderId, long productId) {
        return orderProductRepository.findByOrderAndProduct(orderId, productId);
    }

    public OrderProductDTO update(OrderProductDTO orderProductDTO, long orderId, long productId) {
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(orderId, productId);
        Order order = orderRepository.findOrderById(orderId);
        order.setAmount(order.getAmount()+orderProductDTO.getQuantity()*orderProduct.getProduct().getPrice());
        orderRepository.save(order);
        orderProduct.setDateUpdated(LocalDate.now());
        orderProduct.setQuantity(orderProduct.getQuantity()+ orderProductDTO.getQuantity());
        orderProduct.setAmount(orderProduct.getAmount()+ orderProductDTO.getQuantity()* orderProductDTO.getProduct().getPrice());
        OrderProduct updated = orderProductRepository.save(orderProduct);
        return mapToDTO(updated);
    }
}
