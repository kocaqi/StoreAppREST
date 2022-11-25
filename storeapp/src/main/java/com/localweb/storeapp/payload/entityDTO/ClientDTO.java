package com.localweb.storeapp.payload.entityDTO;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@JsonIdentityReference(alwaysAsId=true)
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ClientDTO {
    @Min(value = 0)
    private long id;
    @NotEmpty(message = "This Field cannot be null!")
    private String firstName;
    @NotEmpty(message = "This Field cannot be null!")
    private String lastName;
    @NotEmpty(message = "This Field cannot be null!")
    private String email;
}
