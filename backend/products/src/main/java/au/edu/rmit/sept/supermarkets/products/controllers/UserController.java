package au.edu.rmit.sept.supermarkets.products.controllers;

import au.edu.rmit.sept.supermarkets.products.models.User;
import au.edu.rmit.sept.supermarkets.products.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/users")
public class UserController {
    UserService service;
    @Autowired
    public UserController(UserService service){this.service =service;}

    @CrossOrigin
    @GetMapping
    public List<User> returnAll(){
        return service.returnAll();
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public User getByID(@PathVariable int id){
        return service.findByID(id);
    }
    @CrossOrigin
    @GetMapping("email/{email}")
        public User getByEmail(@PathVariable String email){
        return service.findByEmail(email);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if (service.deleteUser(id)){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        if (service.addUser(user.Name(),user.Email(), user.Address(), user.Password())){
            return ResponseEntity.status(201).body("A new resource was successfully created.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> attemptLogin(@RequestBody User user){
        if (service.attemptLogin(user.Email(), user.Password())){
            return ResponseEntity.status(200).body("The request was successfully completed.");
        }
        return ResponseEntity.status(400).body("The request was invalid.");
    }
}
