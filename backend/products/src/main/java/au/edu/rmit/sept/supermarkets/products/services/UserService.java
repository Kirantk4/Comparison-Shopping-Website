package au.edu.rmit.sept.supermarkets.products.services;

import au.edu.rmit.sept.supermarkets.products.models.User;

import java.util.List;

public interface UserService {
    List<User> returnAll();
    User findByID(int id);
    User findByEmail(String Email);
    boolean deleteUser(int id);
    boolean addUser(String Name,String Email, String address,String Password);
    boolean attemptLogin(String Email,String Password);
}
