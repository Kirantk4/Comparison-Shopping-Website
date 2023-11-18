package au.edu.rmit.sept.supermarkets.products.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import au.edu.rmit.sept.supermarkets.products.models.Cart;
import au.edu.rmit.sept.supermarkets.products.models.iteminfo;
import au.edu.rmit.sept.supermarkets.products.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartControllerTest {
    CartService service;
    CartController controller;
    @BeforeEach
    void setup(){
        service = mock(CartService.class);
        controller = new CartController(service);
    }
    @Test
    void returnEmptyWhenNoResults(){
        when(service.getCarts()).thenReturn(new ArrayList<>());
        assertEquals(0,controller.all().size());
    }
    @Test
    void returnResultsWhenAvailable(){
        Cart cart = new Cart(1,1,new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(service.getCarts()).thenReturn(list);
        assertEquals(1,controller.all().size());
    }
    @Test
    void returnSpecificCartWhenSearched(){
        Cart cart = new Cart(1,1,new HashMap<>());
        when(service.getCartByID(1)).thenReturn(cart);
        assertEquals(1,controller.findByID(1).getCartID());
    }
    @Test
    void addProductTest(){
        iteminfo info = new iteminfo(1,1,1);
        when(service.addItemToCart(1,1,1)).thenReturn(true);
        assertEquals(200,controller.addItemToCart(info).getStatusCode().value());
    }
    @Test
    void removeProductTest(){
        iteminfo info = new iteminfo(1,1,1);
        when(service.removeItemFromCart(1,1)).thenReturn(true);
        assertEquals(200,controller.removeItemFromCart(info).getStatusCode().value());
    }
    @Test
    void incrementUpTest(){
        iteminfo info = new iteminfo(1,1,1);
        when(service.incrementQuantityUp(1,1)).thenReturn(true);
        assertEquals(200,controller.incrementQuantityUp(info).getStatusCode().value());
    }
    @Test
    void incrementDownTest(){
        iteminfo info = new iteminfo(1,1,1);
        when(service.incrementQuantityDown(1,1)).thenReturn(true);
        assertEquals(200,controller.incrementQuantityDown(info).getStatusCode().value());
    }
    @Test
    void SetQuantityTest(){
        iteminfo info = new iteminfo(1,1,5);
        when(service.setQuantity(1,1,5)).thenReturn(true);
        assertEquals(200,controller.setQuantity(info).getStatusCode().value());
    }
    @Test
    void CheckoutTest(){
        when(service.checkout(1)).thenReturn((Integer) 1);
        assertEquals(1,controller.checkout(1));
    }
}
