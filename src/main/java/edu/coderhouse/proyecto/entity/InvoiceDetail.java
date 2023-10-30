package edu.coderhouse.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "INVOICE_DETAIL")
public class InvoiceDetail {
    //CONSTRUCTORES
    public InvoiceDetail(){
        super();
    }

    public InvoiceDetail(int id, Invoice invoice, int amount, Product product, Long price){
        super();
        this.id = id;
        this.invoice = invoice;
        this.amount = amount;
        this.product = product;
        this.price = price;
    }

    //RELACIONES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_detail_id")
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "inv_detail_inv_id")
    private Invoice invoice;

    @Column(name = "inv_detail_amount")
    private int amount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "inv_detail_prod_id")
    private Product product;

    @Column(name = "inv_detail_price")
    private Long price;


    //GETTERES Y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
