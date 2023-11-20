package au.edu.rmit.sept.supermarkets.products.services;

import au.edu.rmit.sept.supermarkets.products.PasswordHasher;
import au.edu.rmit.sept.supermarkets.products.models.User;
import au.edu.rmit.sept.supermarkets.products.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserRepository repository;
    @Autowired
    public UserServiceImpl(UserRepository repository){this.repository =repository;}

    public List<User> returnAll(){
        return repository.returnAll();
    }
    public User findByID(int id){
        for (User user:repository.returnAll()){
            if (user.id() ==id){
                return user;
            }
        }
        return null;
    }
    public User findByEmail(String Email){
        for (User user:repository.returnAll()){
            if (user.Email().equals(Email)){
                return user;
            }
        }
        return null;
    }
    public boolean addUser(String Name,String Email, String address,String Password){
        //ensure the Email is unique
        for (User user:repository.returnAll()){
            if (user.Email().equals(Email)){
                return false;
            }
        }
        //hash the password before inserting it into the db
        PasswordHasher ph = new PasswordHasher();
        Password = ph.Hashpassword(Password);
        return repository.createUser(Name,Email,address,Password);
    }

    @Override
    public boolean deleteUser(int id) {
        return repository.deleteUser(id);
    }

    @Override
    public boolean attemptLogin(String Email, String Password) {
        PasswordHasher pr = new PasswordHasher();
        for (User user:repository.returnAll()){
            if (user.Email().equals(Email)){
                return user.Password().equals(pr.Hashpassword(Password));
            }
        }
        return false;
    }
}
