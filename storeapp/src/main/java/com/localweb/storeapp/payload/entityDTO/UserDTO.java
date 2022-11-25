package com.localweb.storeapp.payload.entityDTO;

import com.localweb.storeapp.annotation.Email;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UserDTO {
    @Min(value = 0)
    private long id;
    @NotEmpty(message = "This Field cannot be null!")
    private String firstName;
    @NotEmpty(message = "This Field cannot be null!")
    private String lastName;
    @NotEmpty(message = "This Field cannot be null!")
    @Email
    private String email;
    @NotEmpty
    private List<RoleDTO> roles;// = new ArrayList<>();
}
