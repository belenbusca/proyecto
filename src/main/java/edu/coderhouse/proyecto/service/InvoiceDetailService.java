package edu.coderhouse.jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.coderhouse.jpa.Repository.InvoiceDetailRepository;
import edu.coderhouse.jpa.entity.Invoice;
import edu.coderhouse.jpa.entity.InvoiceDetail;

@Service
public class InvoiceDetailService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    
    //invoiceDetailService.save(invoice.getInvoiceDetails(), invoiceSaved.getId());
    public void save(List<InvoiceDetail> details, Invoice invoiceSaved){
        for (InvoiceDetail detail : details) {
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
