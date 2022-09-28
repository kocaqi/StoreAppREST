package com.localweb.storeapp.controller;

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
    public List<OrderDTO> getAll(){
        return orderService.getAll();
    }
}
