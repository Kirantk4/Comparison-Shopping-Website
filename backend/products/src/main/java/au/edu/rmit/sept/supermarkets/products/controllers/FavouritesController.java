package au.edu.rmit.sept.supermarkets.products.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import au.edu.rmit.sept.supermarkets.products.models.Favourite;
import au.edu.rmit.sept.supermarkets.products.services.FavouriteService;

@Controller
@RequestMapping(value = "v1/favourites")
public class FavouritesController {
    FavouriteService service;

    @Autowired
    public FavouritesController(FavouriteService service){this.service = service;}

    @GetMapping
    public List<Favourite> returnAll(){
        return service.returnAll();
    }
    @GetMapping("/{id}")
    public List<Favourite> getByID(@PathVariable int id){
        return service.getByID(id);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addFavourite(@RequestBody Favourite favourites){
        if(service.addFavourite(favourites.customerid(),favourites.productid())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }return ResponseEntity.status(400).body("The request was invalid.");
    }
    @PostMapping("/remove")
    public ResponseEntity<String> removeFavourite(@RequestBody Favourite favourites){
        if(service.removeFavourite(favourites.customerid(),favourites.productid())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }return ResponseEntity.status(400).body("The request was invalid.");
    }
}
