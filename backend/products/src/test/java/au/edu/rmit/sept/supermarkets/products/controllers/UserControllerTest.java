package au.edu.rmit.sept.supermarkets.products.controllers;

import au.edu.rmit.sept.supermarkets.products.models.User;
import au.edu.rmit.sept.supermarkets.products.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    UserService service;
    UserController controller;
    @BeforeEach
    void setup(){
        service = mock(UserService.class);
        controller = new UserController(service);
    }
    @Test
    void returnEmptyWhenNoResults(){
        when(service.returnAll()).thenReturn(new ArrayList<>());
        assertEquals(0,controller.returnAll().size());
    }
    @Test
    void returnResultsWhenAvailable(){
        User user = new User(1,"test","test","test","test");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        when(service.returnAll()).thenReturn(list);
        assertEquals(1,controller.returnAll().size());
    }
    @Test
    void returnSpecificUserWhenSearched(){
        User user = new User(1,"test","test","test","test");
        when(service.findByID(1)).thenReturn(user);
        assertEquals(1,controller.getByID(1).id());
    }
    @Test
    void deleteUserWhenAvailable(){
        when(service.deleteUser(1)).thenReturn(true);
        assertEquals(200,controller.deleteUser(1).getStatusCode().value());
    }
    @Test
    void InvalidDeleteUser(){
        when(service.deleteUser(1)).thenReturn(false);
        assertEquals(400,controller.deleteUser(1).getStatusCode().value());
    }
    @Test
    void createUser(){
        User user = new User(1,"test","test","test","test");
        when(service.addUser("test", "test", "test", "test")).thenReturn(true);
        assertEquals(201,controller.createUser(user).getStatusCode().value());
    }
    @Test
    void InvalidcreateUser(){
        User user = new User(1,"test","test","test","test");
        when(service.addUser("test", "test", "test", "test")).thenReturn(false);
        assertEquals(400,controller.createUser(user).getStatusCode().value());
    }
    @Test
    void attemptLogin(){
        User user = new User(1,"test","test","test","test");
        when(service.attemptLogin("test", "test")).thenReturn(true);
        assertEquals(200,controller.attemptLogin(user).getStatusCode().value());
    }
        @Test
    void invalidattemptLogin(){
        User user = new User(1,"test","test","test","test");
        when(service.attemptLogin("test", "test")).thenReturn(false);
        assertEquals(400,controller.attemptLogin(user).getStatusCode().value());
    }
}
