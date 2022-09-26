package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
