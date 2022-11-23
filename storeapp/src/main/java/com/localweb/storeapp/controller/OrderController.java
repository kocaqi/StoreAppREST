package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.OrderDTO;
import com.localweb.storeapp.payload.entityDTO.OrderProductDTO;
import com.localweb.storeapp.service.OrderProductService;
import com.localweb.storeapp.service.OrderService;
import com.localweb.storeapp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderProductService orderProductService,
                           ProductService productService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    //create order
    @PostMapping("/create/{clientId}")
    public ResponseEntity<String> create(@PathVariable long clientId, Principal principal) {
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
    public ResponseEntity<OrderDTO> getById(@PathVariable(name = "id") long id) {
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
        return ResponseEntity.ok(orderProductService.addProduct(orderProductDTO, orderId).getBody());
    }

    @DeleteMapping("/{orderId}/delete/{productId}")
    public void deleteProduct(@PathVariable("orderId") int orderId,
                              @PathVariable("productId") int productId) {
        orderProductService.delete(orderId, productId);
    }
}
