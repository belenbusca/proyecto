package edu.coderhouse.jpa.entity;

import jakarta.persistence.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "CLIENT")
public class Client {

    // CONSTRUCTORES
    public Client() {
        super();
    }

    public Client(int id, String name, String lastname, String docnumber) {
        super();
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.docnumber = docnumber;
    }

    //RELACIONES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del cliente", example = "1", requiredMode = Schema.RequiredMode.AUTO)
    @Column(name = "client_id")
    private int id;
    
    @Column(name = "client_name")
    @Schema(description = "Nombre del cliente", example = "Juan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Column(name = "client_lastname")
    @Schema(description = "Apellido del cliente", example = "Perez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastname;
    
    @Column(name = "client_docnumber")
    @Schema(description = "DNI del cliente", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String docnumber;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.MERGE)
    private List<Invoice> invoices;
    
    //GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

}
