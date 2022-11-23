package com.localweb.storeapp.payload.entityDTO;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class RoleDTO {
    @Min(value = 0)
    private long id;
}
