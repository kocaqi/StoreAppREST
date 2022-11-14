package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.entityDTO.OrderProductDTO;
import com.localweb.storeapp.payload.entityDTO.ProductDTO;
import com.localweb.storeapp.repository.OrderProductRepository;
import com.localweb.storeapp.repository.OrderRepository;
import com.localweb.storeapp.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public OrderProductDTO create(OrderProductDTO orderProductDTO, int orderId){
        OrderProduct orderProduct = modelMapper.map(orderProductDTO, OrderProduct.class);
        orderProduct.setDateCreated(LocalDate.now());
        orderProduct.setDateUpdated(LocalDate.now());
        orderProduct.setAmount(orderProductDTO.getQuantity() * orderProductDTO.getProduct().getPrice());
        Order order = orderRepository.findOrderById(orderId);
        orderProduct.setOrder(order);
        OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);

        return modelMapper.map(newOrderProduct, OrderProductDTO.class);
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
        return modelMapper.map(updated, OrderProductDTO.class);
    }

    public ResponseEntity<OrderProductDTO> addProduct(OrderProductDTO orderProductDTO, int orderId) {
        Order order = orderRepository.findOrderById(orderId);
        ProductDTO productDTO = orderProductDTO.getProduct();
        Product product = modelMapper.map(productDTO, Product.class);
        long productId = product.getId();

        OrderProduct orderProduct = findByOrderAndProduct(orderId, productId);
        if (orderProduct == null) {
            order.setAmount(order.getAmount() + orderProductDTO.getQuantity()*orderProductDTO.getProduct().getPrice());
            product.setStock(product.getStock() - orderProductDTO.getQuantity());
            productRepository.save(product);
            orderRepository.save(order);
            return new ResponseEntity<>(create(orderProductDTO, orderId), HttpStatus.CREATED);
        } else {
            OrderProductDTO response = update(orderProductDTO, orderId, productId);
            product.setStock(product.getStock() - orderProductDTO.getQuantity());
            productRepository.save(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
