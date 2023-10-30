package edu.coderhouse.jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.coderhouse.jpa.Repository.ProductRepository;
import edu.coderhouse.jpa.entity.Product;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
