package com.localweb.storeapp.payload.entityDTO;

import com.localweb.storeapp.entity.OrderProduct;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProductDTO {
    private int id;
    @NotEmpty(message = "This Field cannot be null!")
    private String name;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    private double price;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    private double stock;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private Set<OrderProduct> orders;
}
