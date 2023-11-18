package au.edu.rmit.sept.supermarkets.products.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import au.edu.rmit.sept.supermarkets.products.repositories.CartRepository;
import au.edu.rmit.sept.supermarkets.products.repositories.CartRepositoryImpl;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartRepositoryTest {
    //no failure tests are required as all possible invalid entry's should be handled by the service
    @Autowired
    Flyway flyway;
    @Autowired
    DataSource source;

    CartRepository repository;

    @BeforeEach
    void Setup(){
        flyway.migrate();
        repository = new CartRepositoryImpl(source);
    }
    @AfterEach
    void tearDown() {
        flyway.clean();
    }
    @Test
    void ReturnsWhenAvailable(){
        assertTrue(0 < repository.returnAll().size());
    }
    @Test
    void DeletesWhenAvailable(){
        // deletes an item from the test cart
        assertTrue(repository.removeitemfromcart(1,1));
    }
    @Test
    void AddsWhenAvailable(){
        //adds an item to a cart when available
        repository.removeitemfromcart(1,1);
        assertTrue(repository.addItemToCart(1,1,4));
    }
    @Test
    void SetQuantityWhenAvailable(){
        //sets the quantity of an item when available
        assertTrue(repository.setQuantity(1,1,10));
    }
    @Test
    void CheckoutWhenAvailable(){
        assertEquals(2,repository.checkout(1));
    }
}
