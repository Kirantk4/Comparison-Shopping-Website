package au.edu.rmit.sept.supermarkets.products.Services;

import au.edu.rmit.sept.supermarkets.products.models.Cart;
import au.edu.rmit.sept.supermarkets.products.repositories.CartRepository;
import au.edu.rmit.sept.supermarkets.products.services.CartService;
import au.edu.rmit.sept.supermarkets.products.services.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class CartServiceTest {
    CartService service;
    CartRepository repository;
    @BeforeEach
    void setup(){
        repository = mock(CartRepository.class);
        service = new CartServiceImpl(repository);
    }
    @Test
    void returnNothingWhenEmpty(){
        when(repository.returnAll()).thenReturn(new ArrayList<>());
        assertEquals(0,service.getCarts().size());
    }
    @Test
    void returnResultsWhenAvailable(){
        Cart cart = new Cart(1,1,new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        assertEquals(1,service.getCarts().size());
    }
    @Test
    void returnCartWhenSearched(){
        //have to return a full list as services findbyID calls the return all to minimise database searches
        Cart cart = new Cart(1,1,new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        assertEquals(cart,service.getCartByID(1));
    }
    @Test
    void returnNullWhenNotFound(){
        when(repository.returnAll()).thenReturn(new ArrayList<>());
        assertNull(service.getCartByID(1));
    }
    @Test
    void validAddItemTest(){
        Cart cart = new Cart(1,1,new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.addItemToCart(1,1,1)).thenReturn(true);
        assertTrue(service.addItemToCart(1,1,1));
    }
    @Test
    void failWhenItemExistsInCart(){
        //attempts to add a product to the cart that is already present in cart
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,1);
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        assertFalse(service.addItemToCart(1,1,1));
    }
    @Test
    void validRemoveItemTest(){
        //has to populate the cart with a product first
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,1);
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.removeitemfromcart(1,1)).thenReturn(true);
        assertTrue(service.removeItemFromCart(1,1));
    }
    @Test
    void failWhenItemThatDoesntExist(){
        //attempts to add a product to the cart that is already present in cart
        Cart cart = new Cart(1,1, new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        assertFalse(service.removeItemFromCart(1,1));
    }
    @Test
    void validIncrementUpTest(){
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,1);
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.setQuantity(1,1,2)).thenReturn(true);
        assertTrue(service.incrementQuantityUp(1,1));
    }
    @Test
    void validIncrementDownTest(){
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,3); //altered to have 3 products so that remove item isnt called
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.setQuantity(1,1,2)).thenReturn(true);
        assertTrue(service.incrementQuantityDown(1,1));
    }
    @Test
    void validSetQuantityTest(){
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,1);
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.setQuantity(1,1,2)).thenReturn(true);
        assertTrue(service.setQuantity(1,1,2));
    }
    @Test
    void callRemoveItemWhenQuantityIsDecrementedToZero(){
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,1);
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.removeitemfromcart(1,1)).thenReturn(true);
        assertTrue(service.incrementQuantityDown(1,1));
    }
    @Test
    void failWhenIncrementingAMissingProduct(){
        Cart cart = new Cart(1,1,new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        assertAll(
                () -> assertFalse(service.incrementQuantityUp(1,1)),
                () -> assertFalse(service.incrementQuantityDown(1,1)),
                () -> assertFalse(service.setQuantity(1,1,1))
        );
    }
    @Test
    void failIncrementWhenCartDoesntExist(){
        ArrayList<Cart> list = new ArrayList<>();
        when(repository.returnAll()).thenReturn(list);
        assertAll(
                () -> assertFalse(service.incrementQuantityUp(1,1)),
                () -> assertFalse(service.incrementQuantityDown(1,1)),
                () -> assertFalse(service.setQuantity(1,1,1))
        );
    }
    @Test
    void validCheckout(){
        HashMap<Integer,Integer> cartcontent = new HashMap<>();
        cartcontent.put(1,1);
        Cart cart = new Cart(1,1,cartcontent);
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.checkout(1)).thenReturn(1);
        assertEquals(1,service.checkout(1));
    }
    @Test
    void invalidCheckoutEmptyCart(){
        Cart cart = new Cart(1,1,new HashMap<>());
        ArrayList<Cart> list = new ArrayList<>();
        list.add(cart);
        when(repository.returnAll()).thenReturn(list);
        when(repository.checkout(1)).thenReturn(1);
        assertEquals(0,service.checkout(1));
    }
    @Test
    void invalidCheckoutNoCart(){
        when(repository.returnAll()).thenReturn(new ArrayList<Cart>());
        when(repository.checkout(1)).thenReturn(1);
        assertEquals(0,service.checkout(1));
    }
}
