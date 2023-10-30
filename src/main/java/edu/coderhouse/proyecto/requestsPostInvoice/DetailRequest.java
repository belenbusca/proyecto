package edu.coderhouse.proyecto.requestsPostInvoice;

import edu.coderhouse.proyecto.entity.Product;

public class DetailRequest {
    private Product product;
    private int amount;

    //CONSTRUCTOR
    public DetailRequest(Product product, int amount){
        this.product = product;
        this.amount = amount;
    }

    //GETTERS AND SETTERS
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
