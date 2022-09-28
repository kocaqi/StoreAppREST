package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.ClientDTO;
import com.localweb.storeapp.payload.OrderDTO;
import com.localweb.storeapp.service.OrderService;
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

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    //create order
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO,
                                               Principal principal){
        orderDTO.setDateCreated(LocalDate.now());
        orderDTO.setDateUpdated(LocalDate.now());
        String email = principal.getName();
        orderDTO.setUser(userService.findUserByEmail(email));
        return new ResponseEntity<>(orderService.create(orderDTO), HttpStatus.CREATED);
    }

    //get all orders
    @GetMapping
    public List<OrderDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
        return orderService.getAll(pageNo, pageSize);
    }

    //get client by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    //update order
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO orderDTO, @PathVariable(name = "id") int id){
        OrderDTO orderResponse = orderService.update(orderDTO, id);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
