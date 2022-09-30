package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
public class ClientDTO {
    int id;
    @NotEmpty(message = "This Field cannot be null!")
    private User theUser;
    @NotEmpty(message = "This Field cannot be null!")
    private String firstName;
    @NotEmpty(message = "This Field cannot be null!")
    private String lastName;
    @NotEmpty(message = "This Field cannot be null!")
    private String email;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<Order> orders;
}
