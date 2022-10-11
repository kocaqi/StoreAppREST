package com.localweb.storeapp.controller;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.payload.entityDTO.OrderDTO;
import com.localweb.storeapp.payload.entityDTO.OrderProductDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.ProductDTO;
import com.localweb.storeapp.service.OrderProductService;
import com.localweb.storeapp.service.OrderService;
import com.localweb.storeapp.service.ProductService;
import com.localweb.storeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;
    UserService userService;
    OrderProductService orderProductService;
    ProductService productService;
    ModelMapper modelMapper;

    public OrderController(OrderService orderService, UserService userService, OrderProductService orderProductService,
                           ProductService productService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    //create order
    @PostMapping("/create")
    public ResponseEntity<String> create(int clientId, Principal principal) {
        return new ResponseEntity<>(orderService.create(clientId, principal), HttpStatus.CREATED);
    }

    //get all orders
    @GetMapping
    public Response<OrderDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
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
    public ResponseEntity<OrderDTO> update(@Valid @RequestBody OrderDTO orderDTO, @PathVariable(name = "id") int id) {
        OrderDTO orderResponse = orderService.update(orderDTO, id);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    //add product to order
    @PostMapping("/{id}/addProduct")
    public ResponseEntity<OrderProductDTO> addProduct(@Valid @RequestBody OrderProductDTO orderProductDTO, @PathVariable("id") int orderId) {
        Order order = orderService.getOrderById(orderId);
        ProductDTO productDTO = orderProductDTO.getProduct();
        Product product = modelMapper.map(productDTO, Product.class);
        int productId = product.getId();

        OrderProduct orderProduct = orderProductService.findByOrderAndProduct(orderId, productId);
        if (orderProduct == null) {
            order.setAmount(order.getAmount() + orderProductDTO.getQuantity()*orderProductDTO.getProduct().getPrice());
            product.setStock(product.getStock() - orderProductDTO.getQuantity());
            productService.save(product);
            orderService.save(order);
            return new ResponseEntity<>(orderProductService.create(orderProductDTO, orderId), HttpStatus.CREATED);
        } else {
            OrderProductDTO response = orderProductService.update(orderProductDTO, orderId, productId);
            product.setStock(product.getStock() - orderProductDTO.getQuantity());
            productService.save(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{orderId}/delete/{productId}")
    public void deleteProduct(@PathVariable("orderId") int orderId,
                              @PathVariable("productId") int productId) {
        orderProductService.delete(orderId, productId);
    }
}
