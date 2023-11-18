package au.edu.rmit.sept.supermarkets.products.Services;
import au.edu.rmit.sept.supermarkets.products.models.User;
import au.edu.rmit.sept.supermarkets.products.repositories.UserRepository;
import au.edu.rmit.sept.supermarkets.products.services.UserService;
import au.edu.rmit.sept.supermarkets.products.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    UserService service;
    UserRepository repository;
    @BeforeEach
    void setup(){
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository);
    }
    @Test
    void returnNothingWhenEmpty(){
        when(repository.returnAll()).thenReturn(new ArrayList<>());
        assertEquals(0,service.returnAll().size());
    }
    @Test
    void returnResultsWhenAvailable(){
        User user = new User(1,"test","test","test","test");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(repository.returnAll()).thenReturn(list);
        assertEquals(1,service.returnAll().size());
    }
    @Test
    void returnCartWhenSearched(){
        //have to return a full list as services findbyID calls the return all to minimise database searches
        User user = new User(1,"test","test","test","test");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(repository.returnAll()).thenReturn(list);
        assertEquals(user,service.findByID(1));
    }
    @Test
    void returnNullWhenNotFound(){
        when(repository.returnAll()).thenReturn(new ArrayList<>());
        assertNull(service.findByID(1));
    }
    @Test
    void ValidAddUser(){
        when(repository.returnAll()).thenReturn(new ArrayList<>());
        //password aquired from running the password hasher as it is required for this function
        when(repository.createUser("test","test","test","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff")).thenReturn(true);
        assertTrue(service.addUser("test","test","test","test"));
    }
    @Test
    void InvalidAddUser(){
        User user = new User(1,"test","test","test","test");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(repository.returnAll()).thenReturn(list);
        assertFalse(service.addUser("test","test","test","test"));
    }
    @Test
    void RemoveUserWhenAvailable(){
        when(repository.deleteUser(1)).thenReturn(true);
        assertTrue(service.deleteUser(1));
    }
    @Test
    void ValidAttemptLogin(){
        //password is just test hashed
        User user = new User(1,"test","test","test","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(repository.returnAll()).thenReturn(list);
        assertTrue(service.attemptLogin("test","test"));
    }
    @Test
    void InvalidLoginWrongEmail(){
        User user = new User(1,"test","test","test","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(repository.returnAll()).thenReturn(list);
        assertFalse(service.attemptLogin("fail","test"));
    }
    @Test
    void InvalidLoginWrongPassword(){
        User user = new User(1,"test","test","test","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(repository.returnAll()).thenReturn(list);
        assertFalse(service.attemptLogin("test","fail"));
    }

}
