package edu.coderhouse.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.coderhouse.proyecto.Repository.InvoiceDetailRepository;
import edu.coderhouse.proyecto.entity.Invoice;
import edu.coderhouse.proyecto.entity.InvoiceDetail;
import edu.coderhouse.proyecto.requestsPostInvoice.DetailRequest;

@Service
public class InvoiceDetailService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    
    //invoiceDetailService.save(invoice.getInvoiceDetails(), invoiceSaved);
    public void save(List<DetailRequest> details, Invoice invoiceSaved){
        for (DetailRequest detail : details) {
			InvoiceDetail invoiceDetailBuilt = new InvoiceDetail();
            invoiceDetailBuilt.setInvoice(invoiceSaved);
            invoiceDetailBuilt.setProduct(detail.getProduct());
            invoiceDetailBuilt.setAmount(detail.getAmount());
            invoiceDetailBuilt.setPrice(detail.getProduct().getPrice());
            invoiceDetailRepository.save(invoiceDetailBuilt);
		}
    }

    public void delete(int id) {
        invoiceDetailRepository.deleteById(id);
    }

    public Optional<InvoiceDetail> findInvoiceDetailById(int id) {
        return invoiceDetailRepository.findById(id);
    }

    public List<InvoiceDetail> findAll(){
        return invoiceDetailRepository.findAll();
    }
    
}
