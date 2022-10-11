package com.localweb.storeapp.payload.entityDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    @JsonIgnore
    int id;
    @NotEmpty(message = "This Field cannot be null!")
    private String firstName;
    @NotEmpty(message = "This Field cannot be null!")
    private String lastName;
    @NotEmpty(message = "This Field cannot be null!")
    private String email;
    @NotEmpty(message = "This Field cannot be null!")
    @Size(min = 4, message = "Password should have more than 4 characters!")
    private String password;
    @NotEmpty
    private List<Integer> rolesIds = new ArrayList<>();
}
