package au.edu.rmit.sept.supermarkets.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.models.DeliveryItem;
import au.edu.rmit.sept.supermarkets.products.services.DeliveryService;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//Lets SpringBoot know this is a controller
@RestController

//Maps all request methods to /v1/deliveries
@RequestMapping(value = "v1/deliveries")

public class DeliveryController {
    private DeliveryService service;

    @Autowired
    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    //Maps all GET requests to /v1/deliveries to this method
    @GetMapping
    public Collection<Delivery> all() {
        return service.getDeliveries();
    }

    @GetMapping("/contents/{id}")
    public Collection<DeliveryItem> deliveryItems(@PathVariable Long id) {
        return service.getDeliveryItems(id);
    }

    //Maps all POST requests to /v1/deliveries to this method
    @PostMapping
    public ResponseEntity<Delivery> insert(@RequestBody Delivery delivery) {
        return new ResponseEntity<Delivery>(service.insert(delivery), HttpStatus.CREATED);
    }

    //Maps all REMOVE request to /v1/deliveries to this method
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeDelivery(@PathVariable Long id) {
        Boolean bool = service.delete(id);
        if (bool){
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Delivery> update(@RequestBody Delivery delivery) {
        return new ResponseEntity<Delivery>(service.update(delivery), HttpStatus.ACCEPTED);
    }
}
