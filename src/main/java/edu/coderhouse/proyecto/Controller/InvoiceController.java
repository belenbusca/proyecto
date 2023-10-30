package edu.coderhouse.jpa.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.coderhouse.jpa.entity.Invoice;
import edu.coderhouse.jpa.service.InvoiceService;
import edu.coderhouse.jpa.service.InvoiceDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Operation(summary = "GET Facturas", description = "Obtiene todas las Facturas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Facturas encontradas"),
        @ApiResponse(responseCode = "404", description = "Facturas no encontradas"),
    })
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Invoice>> getInvoices() {
        return ResponseEntity.ok(invoiceService.findAll());
    }    


    @Operation(summary = "GET Factura", description = "Obtiene una Factura por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Optional<Invoice>> getInvoiceById(@PathVariable int id){
        Optional<Invoice> invoice = invoiceService.findInvoiceById(id);
        if(invoice.isPresent()){
            return ResponseEntity.ok(invoice);
        } else{
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "POST Factura", description = "Crea una Factura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura creada"),
            @ApiResponse(responseCode = "400", description = "Factura no creada")
    })
    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE}, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice invoice){
        try{
            Invoice invoiceSaved = invoiceService.save(invoice);
            invoiceDetailService.save(invoice.getInvoiceDetails(), invoiceSaved);
            return ResponseEntity.ok(invoiceSaved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     MODIFICAR POST PARA QUE EL REQUEST TENGA UN BODY CON LA SIG ESTRUCTURA:
     {
        "cliente": {
            "clienteid":1 }, 
        "lineas" : [ 
                        {
                            "cantidad": 1, 
                            "producto" : {
                                "productoid":1 } } ] }
     */
}
