package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private int id;
    @NotEmpty(message = "This Field cannot be null!")
    private User user;
    @NotEmpty(message = "This Field cannot be null!")
    private Client client_id;
    @NotNull(message = "This Field cannot be null!")
    @Min(value = 0, message = "Please enter positive number!")
    private double amount;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<OrderProduct> orderProducts;
}
