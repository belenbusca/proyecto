package edu.coderhouse.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.coderhouse.proyecto.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    
}
