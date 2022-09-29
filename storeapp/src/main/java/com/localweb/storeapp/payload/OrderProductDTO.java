package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderProductDTO {
    int id;
    Order order;
    Product product;
    double quantity;
    double amount;
    LocalDate dateCreated;
    LocalDate dateUpdated;
}
