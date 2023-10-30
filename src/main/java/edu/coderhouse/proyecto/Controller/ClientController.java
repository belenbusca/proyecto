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

import edu.coderhouse.jpa.entity.Client;
import edu.coderhouse.jpa.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "GET clientes", description = "Obtiene todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
        @ApiResponse(responseCode = "404", description = "Clientes no encontrados"),
    })
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(clientService.findAll());
    }
    

    @Operation(summary = "GET cliente", description = "Obtiene un cliente por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Client>> getClientById(@PathVariable int id){
        Optional<Client> client = clientService.findClientById(id);
        if(client.isPresent()){
            return ResponseEntity.ok(client);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "POST cliente", description = "Crea un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado"),
            @ApiResponse(responseCode = "400", description = "Cliente no creado")
    })
    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        try {
            Client clientSaved = clientService.save(client);
            return ResponseEntity.ok(clientSaved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
