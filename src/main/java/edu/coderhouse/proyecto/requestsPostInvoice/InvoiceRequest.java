package edu.coderhouse.proyecto.requestsPostInvoice;

import java.util.List;

import edu.coderhouse.proyecto.entity.Client;

public class InvoiceRequest {
    private Client client;
    private List<DetailRequest> details;

    //CONSTRUCTOR
    public InvoiceRequest(Client client, List<DetailRequest> details){
        this.client = client;
        this.details = details;
    }

    //GETTERS Y SETTERS
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<DetailRequest> getDetails() {
        return details;
    }

    public void setDetails(List<DetailRequest> details) {
        this.details = details;
    }
}
