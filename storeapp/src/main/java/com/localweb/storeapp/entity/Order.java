package com.localweb.storeapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id", nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private User user;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="client_id", nullable = false)
    @JsonBackReference(value = "client-order")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Client client_id;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "date_created")
    private LocalDate dateCreated;
    @Column(name = "date_updated")
    private LocalDate dateUpdated;
    @OneToMany(mappedBy = "order")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderProduct> orderProducts;

    public Order() {
    }

    public Order(long id, User user, Client client_id, double amount, LocalDate dateCreated, LocalDate dateUpdated, List<OrderProduct> orderProducts) {
        this.id = id;
        this.user = user;
        this.client_id = client_id;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.orderProducts = orderProducts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client) {
        this.client_id = client;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orders) {
        this.orderProducts = orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}

