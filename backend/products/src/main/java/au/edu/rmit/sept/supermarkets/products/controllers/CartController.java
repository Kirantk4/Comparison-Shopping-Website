package au.edu.rmit.sept.supermarkets.products.controllers;

import au.edu.rmit.sept.supermarkets.products.models.Cart;
import au.edu.rmit.sept.supermarkets.products.models.iteminfo;
import au.edu.rmit.sept.supermarkets.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "v1/carts")
public class CartController {
    private CartService service;

    @Autowired
    public CartController(CartService service){this.service = service;}

    @GetMapping
    public List<Cart> all() {return service.getCarts();}

    @GetMapping("/{cartid}")
    public Cart findByID(@PathVariable int cartid){
        return service.getCartByID(cartid);
    }

    @GetMapping("/customer/{custid}")
    public Cart findByCustomerID(@PathVariable int custid){
        return service.getCartByCustomerID(custid);
    }


    @CrossOrigin
    @PostMapping("/delete")
    public ResponseEntity<String> removeItemFromCart(@RequestBody iteminfo item){
        if (service.removeItemFromCart(item.cartid(),item.productid())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestBody iteminfo item){
        if (service.addItemToCart(item.cartid(), item.productid(), item.quantity())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    @CrossOrigin
    @PostMapping("/increment")
    public ResponseEntity<String> incrementQuantityUp(@RequestBody iteminfo item){
        if (service.incrementQuantityUp(item.cartid(), item.productid())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    @CrossOrigin
    @PostMapping("/decrement")
    public ResponseEntity<String> incrementQuantityDown(@RequestBody iteminfo item){
        if (service.incrementQuantityDown(item.cartid(), item.productid() )){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    @CrossOrigin
    @PostMapping("/set")
    public ResponseEntity<String> setQuantity(@RequestBody iteminfo item){
        if (service.setQuantity(item.cartid(), item.productid(), item.quantity())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    //returns the delivery order id
    @CrossOrigin
    @GetMapping("/checkout/{cartid}")
    public Integer checkout(@PathVariable int cartid){
        return service.checkout(cartid);
    }

    @CrossOrigin
    @PostMapping("/new/{customerId}")
    public ResponseEntity<String> createCartForCustomer(@PathVariable int customerId) {
        if (service.createCartForCustomer(customerId)) {
            return ResponseEntity.status(200).body("New cart created for customer.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
}
