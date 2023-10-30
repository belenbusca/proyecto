package edu.coderhouse.jpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.coderhouse.jpa.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
