package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private int id;
    private User user;
    private Client client_id;
    private double amount;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<OrderProduct> orderProducts;
}
