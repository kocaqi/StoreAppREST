package com.localweb.storeapp.controller;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.OrderDTO;
import com.localweb.storeapp.payload.OrderProductDTO;
import com.localweb.storeapp.service.OrderProductService;
import com.localweb.storeapp.service.OrderService;
import com.localweb.storeapp.service.ProductService;
import com.localweb.storeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;
    UserService userService;
    OrderProductService orderProductService;
    ProductService productService;

    public OrderController(OrderService orderService, UserService userService, OrderProductService orderProductService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderProductService = orderProductService;
        this.productService = productService;
    }

    //create order
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO,
                                           Principal principal) {
        orderDTO.setDateCreated(LocalDate.now());
        orderDTO.setDateUpdated(LocalDate.now());
        String email = principal.getName();
        orderDTO.setUser(userService.findUserByEmail(email));
        return new ResponseEntity<>(orderService.create(orderDTO), HttpStatus.CREATED);
    }

    //get all orders
    @GetMapping
    public List<OrderDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return orderService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    //get client by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    //update order
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO orderDTO, @PathVariable(name = "id") int id) {
        OrderDTO orderResponse = orderService.update(orderDTO, id);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    //add product to order
    @PostMapping("/{id}/addProduct")
    public ResponseEntity<OrderProductDTO> addProduct(@RequestBody OrderProductDTO orderProductDTO, @PathVariable("id") int orderId) {
        Order order = orderService.getOrderById(orderId);
        orderProductDTO.setOrder(order);
        int productId = orderProductDTO.getProduct().getId();
        Product product = productService.findProductById(productId);
        orderProductDTO.setProduct(product);

        OrderProduct orderProduct = orderProductService.findByOrderAndProduct(orderId, productId);
        if (orderProduct == null) {
            orderProductDTO.setDateCreated(LocalDate.now());
            orderProductDTO.setDateUpdated(LocalDate.now());
            orderProductDTO.setAmount(orderProductDTO.getQuantity() * orderProductDTO.getProduct().getPrice());
            order.setAmount(order.getAmount() + orderProductDTO.getAmount());
            orderService.save(order);
            return new ResponseEntity<>(orderProductService.create(orderProductDTO), HttpStatus.CREATED);
        }
        else{
            OrderProductDTO response = orderProductService.update(orderProductDTO, orderId, productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{orderId}/delete/{productId}")
    public void deleteProduct(@PathVariable("orderId") int orderId,
                              @PathVariable("productId") int productId) {
        orderProductService.delete(orderId, productId);
    }
}
