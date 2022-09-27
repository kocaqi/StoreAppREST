package com.localweb.storeapp.payload;

import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.entity.User;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class ClientDTO {
    int id;
    private User theUser;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<Order> orders;
}
