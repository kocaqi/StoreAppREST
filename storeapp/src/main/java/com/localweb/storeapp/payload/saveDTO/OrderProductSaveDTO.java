package com.localweb.storeapp.payload.saveDTO;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderProductSaveDTO {
    @NotNull(message = "This Field cannot be null!")
    long productId;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    double quantity;
}



