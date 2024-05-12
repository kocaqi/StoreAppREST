package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.OrderDTO;
import com.localweb.storeapp.payload.entityDTO.OrderProductDTO;
import com.localweb.storeapp.payload.saveDTO.OrderProductSaveDTO;
import com.localweb.storeapp.service.OrderProductService;
import com.localweb.storeapp.service.OrderService;
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

    @Autowired
    public OrderController(OrderService orderService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    //create order
    @PostMapping("/create/{clientId}")
    public ResponseEntity<OrderDTO> create(@PathVariable long clientId, Principal principal) {
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

    //get order by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    //add product to order
    @PostMapping("/{orderId}/addProduct")
    public ResponseEntity<OrderProductDTO> addProduct(@Valid @RequestBody OrderProductSaveDTO orderProductSaveDTO, @PathVariable("orderId") int orderId) {
        return new ResponseEntity<>(orderProductService.addProduct(orderProductSaveDTO, orderId), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/remove_product/{productId}")
    public void removeProduct(@PathVariable("orderId") int orderId,
                              @PathVariable("productId") int productId) {
        orderProductService.removeProduct(orderId, productId);
    }
}
