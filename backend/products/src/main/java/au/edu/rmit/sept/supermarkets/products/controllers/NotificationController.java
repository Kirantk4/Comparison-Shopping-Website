package au.edu.rmit.sept.supermarkets.products.controllers;

import org.springframework.web.bind.annotation.RestController;



import au.edu.rmit.sept.supermarkets.products.models.Notification;

import au.edu.rmit.sept.supermarkets.products.services.NotificationsService;

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
@RequestMapping(value = "v1/notifications")

public class NotificationController {
    private NotificationsService service;

    @Autowired
    public NotificationController(NotificationsService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Notification> all() {
        return service.getNotifications();
    }

    @PostMapping
    public ResponseEntity<Notification> insert(@RequestBody Notification notification) {
        return new ResponseEntity<Notification>(service.insert(notification), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeNotification(@PathVariable Long id) {
        Boolean bool = service.delete(id);
        if (bool){
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping
    public ResponseEntity<Notification> update(@RequestBody Notification notification) {
        return new ResponseEntity<Notification>(service.update(notification), HttpStatus.ACCEPTED);
    }

}
