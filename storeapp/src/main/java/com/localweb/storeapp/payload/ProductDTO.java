package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.OrderProduct;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private double stock;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private Set<OrderProduct> orders;
}
