package com.localweb.storeapp.payload.entityDTO;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.OrderProduct;
import com.localweb.storeapp.entity.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    @NotNull(message = "This Field cannot be null!")
    private Client client_id;
}
