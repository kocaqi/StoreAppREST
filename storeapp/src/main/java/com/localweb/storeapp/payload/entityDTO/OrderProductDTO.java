package com.localweb.storeapp.payload.entityDTO;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderProductDTO {
    @Min(value = 0)
    private long id;
    @NotNull(message = "This Field cannot be null!")
    ProductDTO product;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    double quantity;
}



