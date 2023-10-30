package edu.coderhouse.jpa.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product {
    
    //CONSTRUCTORES
    public Product(){
        super();
    }

    public Product(int id, String description, String code, int stock, Long price){
        super();
        this.id = id;
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }

    //RELACIONES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private int id;

    @Column(name = "prod_description")
    private String description;

    @Column(name = "prod_code")
    private String code;

    @Column(name = "prod_stock")
    private int stock;

    @Column(name = "prod_price")
    private Long price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    @JsonIgnore
    @Schema(description = "Detalle del Comprobante", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<InvoiceDetail> invoiceDetails;

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    
}
