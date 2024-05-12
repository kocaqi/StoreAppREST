package com.localweb.storeapp.payload.saveDTO;

import com.localweb.storeapp.annotation.Email;
import com.localweb.storeapp.payload.entityDTO.RoleDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserSaveDTO {
    @NotEmpty(message = "This Field cannot be null!")
    private String firstName;
    @NotEmpty(message = "This Field cannot be null!")
    private String lastName;
    @NotEmpty(message = "This Field cannot be null!")
    @Email
    private String email;
    @NotEmpty(message = "This Field cannot be null!")
    @Size(min = 4, message = "Password should have more than 4 characters!")
    private String password;
    @NotEmpty
    private List<RoleDTO> roles;// = new ArrayList<>();
}



