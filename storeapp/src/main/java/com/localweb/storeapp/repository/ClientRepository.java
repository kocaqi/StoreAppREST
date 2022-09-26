package com.localweb.storeapp.repository;

import com.localweb.thelogin.thelogin.entities.Client;
import com.localweb.thelogin.thelogin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client getClientById(int id);

    List<Client> findAllByTheUser(User user);
}
