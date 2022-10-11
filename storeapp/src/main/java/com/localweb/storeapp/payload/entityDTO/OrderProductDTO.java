package com.localweb.storeapp.payload.entityDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.Product;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class OrderProductDTO {
    @JsonIgnore
    int id;
    @NotNull(message = "This Field cannot be null!")
    ProductDTO product;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    double quantity;
}
