package com.localweb.storeapp.payload.entityDTO;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {
    @Min(value = 0)
    private long id;
    @NotEmpty(message = "This Field cannot be null!")
    private String name;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    private double price;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    private double stock;
}







