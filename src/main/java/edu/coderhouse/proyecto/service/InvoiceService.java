package edu.coderhouse.jpa.service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import edu.coderhouse.jpa.Repository.InoviceRepository;
import edu.coderhouse.jpa.Repository.ProductRepository;
import edu.coderhouse.jpa.entity.Client;
import edu.coderhouse.jpa.entity.Invoice;
import edu.coderhouse.jpa.entity.InvoiceDetail;
import edu.coderhouse.jpa.entity.WorldClock;

@Service
public class InvoiceService {
    @Autowired
    private InoviceRepository inoviceRepository;
    // para validar existencia de cliente y producto:
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    public void delete(int id){
        inoviceRepository.deleteById(id);
    }

    public Optional<Invoice> findInvoiceById(int id){
        return inoviceRepository.findById(id);
    }

    public List<Invoice> findAll() {
        return inoviceRepository.findAll();
    }
    
    
    public Invoice save(Invoice invoice) {
        Boolean clientExists = clientExists(invoice.getClient());
        Boolean productExists = productExists(invoice.getInvoiceDetails());
        Boolean enoughStock = enoughStock(invoice.getInvoiceDetails());

        if(clientExists && productExists && enoughStock) {
            // actualizar stock y MOSTRARLO
            var invoiceToSave = buildInvoice(invoice);
            updateStock(invoiceToSave.getInvoiceDetails());
            return inoviceRepository.save(invoice);
        }
        else if(!clientExists){
            throw new RuntimeException("Client doesnt exist");
        } else if(!productExists){
            throw new RuntimeException("Product doesnt exist");
        } else{
            throw new RuntimeException("Not enough stock");
        }
            
    }
    // + validaciones
    private Boolean clientExists(Client client){
        var opCliente = clientService.findClientById(client.getId()); 
		return !opCliente.isEmpty();
    }

    private Boolean productExists(List<InvoiceDetail> details){
        for (InvoiceDetail detail : details) {
			var productoId = detail.getProduct().getId();
			var opProducto = productService.findProductById(productoId);
			if (opProducto.isEmpty()) {
				return false;
			}
		}
		return true;
    }

    private Boolean enoughStock(List<InvoiceDetail> details){
        for (InvoiceDetail detail : details) {
			var productId = detail.getProduct().getId();
			var opProduct = productService.findProductById(productId);
			if (opProduct.isEmpty()) {
				return false;
			}
            if(opProduct.get().getStock() < detail.getAmount())
                return false;
		}
		return true;
    }

    private Invoice buildInvoice(Invoice invoice){
        var invoiceToSave = new Invoice();
        invoiceToSave.setClient(clientService.findClientById(invoice.getClient().getId()).get());
        
        // set created at con el serv externo
        invoiceToSave.setCreated_at(checkAndBuildDate());
          
        invoiceToSave.setTotal(totalPrice(invoiceToSave.getInvoiceDetails()));
        return invoiceToSave;
    }

    private void updateStock(List<InvoiceDetail> details) {
		for (InvoiceDetail detail : details) {
			var invoiceAmount = detail.getAmount();
			var product = detail.getProduct();
			
			var productDB = productService.findProductById(product.getId()).get();
			var stock = productDB.getStock();
			var nuevoStock = stock - invoiceAmount;
			productDB.setStock(nuevoStock);
			
			this.productRepository.save(productDB);
		}
	}

    private Date checkAndBuildDate(){
        RestTemplate restTemplate = new RestTemplate();
        WorldClock worldClock = restTemplate.getForObject("http://worldclockapi.com/api/json/utc/now", WorldClock.class);
        
		String currentDate = worldClock.getCurrentDateTime();
		try {
			Date dateApi = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(currentDate); // ej '2023-09-19 14:32:36'
			return dateApi;
		} catch (ParseException e) {
			e.printStackTrace();
            Date dateErrorApi = new Date();
			return dateErrorApi; 
		}
    }

    private Long totalPrice(List<InvoiceDetail> details){
        Long totalInvoice = 0L;
        for (InvoiceDetail detail : details) {
			Long totalDetail = detail.getAmount() * detail.getPrice();
            totalInvoice = totalInvoice + totalDetail;
		}
        return totalInvoice;
    }

}
