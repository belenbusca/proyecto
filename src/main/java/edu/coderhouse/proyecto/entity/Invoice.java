package edu.coderhouse.jpa.entity;

import java.util.Date;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
/*import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;*/

@Entity
@Table(name = "INVOICE")
public class Invoice {

    // CONSTRUCTORES
    public Invoice(){
        super();
    }

    public Invoice(int id, Client client, Date created_at, long total){
        super();
        this.id = id;
        this.client = client;
        this.created_at = created_at;
        this.total = total;
    }

    //RELACIONES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_id")
    @Schema(description = "Identificador del Comprobante", example = "1", requiredMode = Schema.RequiredMode.AUTO)
    private int id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="inv_client_id") //!!!! no se si esta bien, sera client_id en vez de inv_client_id?
    @Schema(description = "ID Cliente", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
	private Client client;
    
    @Schema(description = "Momento de creacion", example = "2023-09-19 14:30:01", hidden = true)
    @Column(name = "inv_created_at")
    private Date created_at;
    
    @Schema(description = "Total", example = "1834.41", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "inv_total")
    private long total;

    //@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL) //DETAIL DEPENDE DE INVOICE, por eso all y no marge
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Detalle del Comprobante", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<InvoiceDetail> invoiceDetails;

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }  

    /*public InvoiceDetail addLinea(InvoiceDetail detail) {
		getInvoiceDetails().add(detail);
		detail.setInvoice(this);
		return detail;
	}

	public InvoiceDetail removeLinea(InvoiceDetail detail) { // o hago void y no las devuelve TODO!!!
		getInvoiceDetails().remove(detail);
		detail.setInvoice(null);
        return detail;
	}*/
}
