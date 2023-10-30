package edu.coderhouse.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.coderhouse.proyecto.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
