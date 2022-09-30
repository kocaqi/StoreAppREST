package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private int id;
    @NotEmpty(message = "This Field cannot be null!")
    private String firstName;
    @NotEmpty(message = "This Field cannot be null!")
    private String lastName;
    @NotEmpty(message = "This Field cannot be null!")
    private String email;
    @NotEmpty(message = "This Field cannot be null!")
    private String password;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    //@NotEmpty(message = "This Field cannot be null!")
    private List<Role> roles = new ArrayList<>();
    private int enabled;
    private List<Client> clients;
    private List<Order> orders;
}
