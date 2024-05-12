package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.entityDTO.OrderProductDTO;
import com.localweb.storeapp.payload.saveDTO.OrderProductSaveDTO;
import com.localweb.storeapp.repository.OrderProductRepository;
import com.localweb.storeapp.repository.OrderRepository;
import com.localweb.storeapp.repository.ProductRepository;
import com.localweb.storeapp.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository,
                               ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public OrderProductDTO create(OrderProductSaveDTO orderProductSaveDTO, long orderId){
        OrderProduct orderProduct = modelMapper.map(orderProductSaveDTO, OrderProduct.class);
        orderProduct.setDateCreated(LocalDate.now());
        orderProduct.setDateUpdated(LocalDate.now());
        Product product = productRepository.findProductById(orderProductSaveDTO.getProductId())
                .orElseThrow(()->new ResourceNotFoundException("Product", "id", orderProductSaveDTO.getProductId()));
        orderProduct.setAmount(orderProductSaveDTO.getQuantity() * product.getPrice());
        Order order = orderRepository.findOrderById(orderId);
        orderProduct.setOrder(order);
        OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);

        return modelMapper.map(newOrderProduct, OrderProductDTO.class);
    }

    @Transactional
    public void removeProduct(long orderId, long productId) {
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(orderId, productId);
        Order order = orderRepository.findOrderById(orderId);
        order.setAmount(order.getAmount()-orderProduct.getAmount());
        Product product = productRepository.findProductById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
        product.setStock(product.getStock()+orderProduct.getQuantity());
        productRepository.save(product);
        orderRepository.save(order);
        orderProductRepository.deleteProduct(orderId, productId);
    }

    @Transactional
    public OrderProduct update(OrderProductSaveDTO orderProductSaveDTO, long orderId, long productId) {
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(orderId, productId);
        Order order = orderRepository.findOrderById(orderId);
        order.setAmount(order.getAmount()+orderProductSaveDTO.getQuantity()*orderProduct.getProduct().getPrice());
        orderRepository.save(order);
        orderProduct.setDateUpdated(LocalDate.now());
        orderProduct.setQuantity(orderProduct.getQuantity()+ orderProductSaveDTO.getQuantity());
        Product product = productRepository.findProductById(orderProductSaveDTO.getProductId())
                .orElseThrow(()->new ResourceNotFoundException("Product", "id", orderProductSaveDTO.getProductId()));
        orderProduct.setAmount(orderProduct.getAmount()+ orderProductSaveDTO.getQuantity()* product.getPrice());
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public OrderProductDTO addProduct(OrderProductSaveDTO orderProductSaveDTO, long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        Product product = productRepository.findProductById(orderProductSaveDTO.getProductId())
                .orElseThrow(()->new ResourceNotFoundException("Product", "id", orderProductSaveDTO.getProductId()));

        long productId = product.getId();

        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(orderId, productId);
        if (orderProduct == null) {
            order.setAmount(order.getAmount() + orderProductSaveDTO.getQuantity()*product.getPrice());
            product.setStock(product.getStock() - orderProductSaveDTO.getQuantity());
            productRepository.save(product);
            orderRepository.save(order);
            return create(orderProductSaveDTO, orderId);
        } else {
            OrderProduct response = update(orderProductSaveDTO, orderId, productId);
            product.setStock(product.getStock() - orderProductSaveDTO.getQuantity());
            productRepository.save(product);
            orderProductRepository.save(response);
            return modelMapper.map(response, OrderProductDTO.class);
        }
    }
}

