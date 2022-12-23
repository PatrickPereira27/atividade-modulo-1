package com.dsCadastro.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsCadastro.cliente.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
