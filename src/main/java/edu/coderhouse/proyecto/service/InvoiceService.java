package edu.coderhouse.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import edu.coderhouse.proyecto.Repository.InoviceRepository;
import edu.coderhouse.proyecto.Repository.ProductRepository;
import edu.coderhouse.proyecto.entity.Client;
import edu.coderhouse.proyecto.entity.Invoice;
import edu.coderhouse.proyecto.entity.InvoiceDetail;
import edu.coderhouse.proyecto.entity.WorldClock;
import edu.coderhouse.proyecto.requestsPostInvoice.DetailRequest;
import edu.coderhouse.proyecto.requestsPostInvoice.InvoiceRequest;

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
    
    
    public Invoice save(InvoiceRequest invoice) {
        Boolean clientExists = clientExists(invoice.getClient());
        Boolean productExists = productExists(invoice.getInvoiceDetails());
        Boolean enoughStock = enoughStock(invoice.getInvoiceDetails());

        if(clientExists && productExists && enoughStock) {
            var invoiceToSave = buildInvoice(invoice);
            updateStock(invoiceToSave.getInvoiceDetails());
            return inoviceRepository.save(invoiceToSave);
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

    private Boolean productExists(List<DetailRequest> details){
        for (DetailRequest detail : details) {
			var productoId = detail.getProduct().getId();
			var opProducto = productService.findProductById(productoId);
			if (opProducto.isEmpty()) {
				return false;
			}
		}
		return true;
    }

    private Boolean enoughStock(List<DetailRequest> details){
        for (DetailRequest detail : details) {
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

    private Invoice buildInvoice(InvoiceRequest invoice){
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
