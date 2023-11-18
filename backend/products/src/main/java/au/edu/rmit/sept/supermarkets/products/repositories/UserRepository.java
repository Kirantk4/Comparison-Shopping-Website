package au.edu.rmit.sept.supermarkets.products.repositories;

import au.edu.rmit.sept.supermarkets.products.models.User;

import java.util.List;

public interface UserRepository {
    public List<User> returnAll();
    public boolean deleteUser(int id);
    public boolean createUser(String Name,String Email, String address,String Password);
}
