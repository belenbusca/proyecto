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

import edu.coderhouse.jpa.entity.Product;
import edu.coderhouse.jpa.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @Operation(summary = "GET Productos", description = "Obtiene todos los Productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "404", description = "Productos no encontrados"),
    })
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "GET Producto", description = "Obtiene un Producto por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable int id){
        Optional<Product> product = productService.findProductById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "POST producto", description = "Crea un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Producto no creado")
    })
    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE}, produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        try{
            Product productSaved = productService.save(product);
            return ResponseEntity.ok(productSaved);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
