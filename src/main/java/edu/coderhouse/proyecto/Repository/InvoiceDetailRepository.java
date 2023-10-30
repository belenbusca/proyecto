package edu.coderhouse.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.coderhouse.proyecto.entity.InvoiceDetail;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer>{
    
}
