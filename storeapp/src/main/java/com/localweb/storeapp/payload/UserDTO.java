package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<Role> roles = new ArrayList<>();
    private int enabled;
    private List<Client> clients;
    private List<Order> orders;
}
