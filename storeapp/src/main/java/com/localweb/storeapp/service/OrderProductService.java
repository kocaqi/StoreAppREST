package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.OrderDTO;
import com.localweb.storeapp.payload.OrderProductDTO;
import com.localweb.storeapp.repository.OrderProductRepository;
import com.localweb.storeapp.repository.OrderRepository;
import com.localweb.storeapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderProductService {

    OrderProductRepository orderProductRepository;
    OrderRepository orderRepository;

    ProductRepository productRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderProductDTO create(OrderProductDTO orderProductDTO){
        OrderProduct orderProduct = mapToEntity(orderProductDTO);
        OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);

        OrderProductDTO orderProductResponse = mapToDTO(newOrderProduct);
        return orderProductResponse;
    }

    private OrderProductDTO mapToDTO(OrderProduct newOrderProduct){
        OrderProductDTO orderProductRespose = new OrderProductDTO();
        orderProductRespose.setId(newOrderProduct.getId());
        orderProductRespose.setOrder(newOrderProduct.getOrder());
        orderProductRespose.setProduct(newOrderProduct.getProduct());
        orderProductRespose.setAmount(newOrderProduct.getAmount());
        orderProductRespose.setQuantity(newOrderProduct.getQuantity());
        orderProductRespose.setDateCreated(newOrderProduct.getDateCreated());
        orderProductRespose.setDateUpdated(newOrderProduct.getDateUpdated());
        return orderProductRespose;
    }

    private OrderProduct mapToEntity(OrderProductDTO orderProductDTO){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(orderProductDTO.getOrder());
        orderProduct.setProduct(orderProductDTO.getProduct());
        orderProduct.setAmount(orderProductDTO.getAmount());
        orderProduct.setQuantity(orderProductDTO.getQuantity());
        orderProduct.setDateCreated(orderProductDTO.getDateCreated());
        orderProduct.setDateUpdated(orderProductDTO.getDateUpdated());
        return orderProduct;
    }

    public void delete(int orderId, int productId) {
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(orderId, productId);
        orderProductRepository.delete(orderProduct);
    }

    public OrderProduct findByOrderAndProduct(int orderId, int productId) {
        return orderProductRepository.findByOrderAndProduct(orderId, productId);
    }

    public OrderProductDTO update(OrderProductDTO orderProductDTO, int orderId, int productId) {
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
