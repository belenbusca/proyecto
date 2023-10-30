package edu.coderhouse.jpa.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.coderhouse.jpa.entity.InvoiceDetail;
import edu.coderhouse.jpa.service.InvoiceDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/invoiceDetail")
public class InvoiceDetailController {

    @Autowired
    private InvoiceDetailService invoiceDetailService;


    @Operation(summary = "GET Detalles de Factura", description = "Obtiene todos los Detalles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles encontrados"),
        @ApiResponse(responseCode = "404", description = "Detalles no encontrados"),
    })
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<InvoiceDetail>> getInvoiceDetail() {
        return ResponseEntity.ok(invoiceDetailService.findAll());
    }    


    @Operation(summary = "GET Detalle de Factura", description = "Obtiene un Detalle por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<InvoiceDetail>> getInvoiceDetailById(@PathVariable int id){
        Optional<InvoiceDetail> invoiceDetail = invoiceDetailService.findInvoiceDetailById(id);
        if(invoiceDetail.isPresent()){
            return ResponseEntity.ok(invoiceDetail);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    //tal vez no lo use si creo los invoice detail desde el post de invoice 
    /*@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceDetail> saveInvoiceDetail(@RequestBody InvoiceDetail invoiceDetail){
        try{
            InvoiceDetail invoiceDetailSaved = invoiceDetailService.save(invoiceDetail);
            return ResponseEntity.ok(invoiceDetailSaved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }*/
}
