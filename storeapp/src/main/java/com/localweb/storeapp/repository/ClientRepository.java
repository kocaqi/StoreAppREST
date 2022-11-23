package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {
    Client findClientById(long id);
}
