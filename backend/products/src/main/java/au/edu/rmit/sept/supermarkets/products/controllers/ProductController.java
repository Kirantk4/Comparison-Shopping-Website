package au.edu.rmit.sept.supermarkets.products.controllers;
import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;
import au.edu.rmit.sept.supermarkets.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping(value = "v1/products")

public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service){this.service = service;}
    
    @CrossOrigin
    @GetMapping
    public HashSet<Product> all() {return service.GetProducts();}

    @CrossOrigin
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return service.GetProduct(id);
    }
    
    @CrossOrigin
    @GetMapping("/price/{id}")
    public HashSet<ItemPrice> getPrice(@PathVariable int id) {
        return service.GetProductPrice(id);
    }

    //Searching must take the form of /search?name=NAME&category=CATEGORY&supermarket=SUPERMARKET&priceUpper=PRICE&priceLower=PRICE&sortBy=NAME&sortOrder=ORDER
    //Sort by is either DEFAULT, NAME, CATEGORY, SUPERMARKET, PRICE
    //Where ORDER is either ASC or DESC
    //If a parameter is not required, leave it blank
    @CrossOrigin
    @GetMapping("/search")
    public HashSet<Product> search(
        @RequestParam(name = "name", required = false)String name,
        @RequestParam(name = "category", required = false)int categoryId,
        @RequestParam(name = "supermarket", required = false)String supermarket,
        @RequestParam(name = "priceUpper", required = false)double priceUpper,
        @RequestParam(name = "priceLower", required = false)double priceLower,
        @RequestParam(name = "sortBy", required = false) String sortBy,
        @RequestParam(name = "sortOrder", required = false) String sortOrder){
            name = name == null ? "" : name;
            supermarket = supermarket == null ? "" : supermarket;
            sortBy = sortBy == null ? "" : sortBy;
            sortOrder = sortOrder == null ? "" : sortOrder;
        return service.search(name, categoryId, supermarket, priceUpper, priceLower, sortBy, sortOrder);
    }
    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product product) {
        return new ResponseEntity<Product>(service.insert(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeProduct(@PathVariable Long id) {
        Boolean bool = service.delete(id);
        if (bool){
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return new ResponseEntity<Product>(service.update(product), HttpStatus.ACCEPTED);
    }

    @PutMapping("/priceUpdate/{id}/{price}/{supermarket}")
    public ResponseEntity<ItemPrice> updatePrice(@PathVariable Long id, @PathVariable double price, @PathVariable String supermarket) {
        return new ResponseEntity<ItemPrice>(service.updatePrice(id, price, supermarket), HttpStatus.ACCEPTED);
    }
    @PostMapping("/priceInsert/{id}/{price}/{supermarket}")
    public ResponseEntity<ItemPrice> insertPrice(@PathVariable Long id, @PathVariable double price, @PathVariable String supermarket) {
        return new ResponseEntity<ItemPrice>(service.insertPrice(id, price, supermarket), HttpStatus.CREATED);
    }

}

