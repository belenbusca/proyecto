package edu.coderhouse.jpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.coderhouse.jpa.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    
}
