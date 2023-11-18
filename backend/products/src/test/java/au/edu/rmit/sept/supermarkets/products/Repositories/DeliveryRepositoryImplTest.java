package au.edu.rmit.sept.supermarkets.products.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.models.DeliveryItem;
import au.edu.rmit.sept.supermarkets.products.repositories.DeliveryRepository;
import au.edu.rmit.sept.supermarkets.products.repositories.DeliveryRepositoryImpl;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DeliveryRepositoryImplTest {
    @Autowired
    Flyway flyway;
    @Autowired
    DataSource source;

    DeliveryRepository repository;

    @BeforeEach
    void Setup(){
        flyway.migrate();
        repository = new DeliveryRepositoryImpl(source);
    }
    @AfterEach
    void tearDown() {
        flyway.clean();
    }

    @Test
    void ReturnsWhenAvailable(){
        var deliveries = repository.findAll();
        Date testDate = Date.valueOf("2020-01-02");
        assertEquals(1 , deliveries.size());
        assertEquals(1, deliveries.get(0).customerId());
        assertEquals(1, deliveries.get(0).orderId());
        assertEquals(testDate, deliveries.get(0).deliveryDate());
    }
    @Test
    void insertDelivery(){
        Date orderDate = Date.valueOf("2020-01-02");
        Date deliveryDate = Date.valueOf("2020-01-04");
        Delivery delivery = new Delivery(2, 1, orderDate, deliveryDate);
        Delivery returned = repository.insert(delivery);
        assertEquals(returned, delivery);
        var deliveries = repository.findAll();
        assertEquals(2, deliveries.size());
    }
    @Test
    void deleteDelivery(){
        assertTrue(repository.delete(1L));
        var deliveries = repository.findAll();
        assertEquals(0, deliveries.size());
    }

    @Test
    void deleteDeliveryNotFound(){
        assertTrue(!repository.delete(2L));
        var deliveries = repository.findAll();
        assertEquals(1, deliveries.size());
    }
    @Test
    void updateDeliveryCorrectId(){
        Date orderDate = Date.valueOf("2020-01-02");
        Date deliveryDate = Date.valueOf("2020-01-04");
        Delivery delivery = new Delivery(1, 1, orderDate, deliveryDate);
        Delivery returned = repository.update(delivery);
        assertEquals(returned, delivery);
        var deliveries = repository.findAll();
        assertEquals(1, deliveries.size());
        assertEquals(1, deliveries.get(0).customerId());
        assertEquals(1, deliveries.get(0).orderId());
        assertEquals(delivery, deliveries.get(0));
        assertEquals(delivery, returned);
    }
    @Test
    void updateDeliveryIncorrectId(){
        Date orderDate = Date.valueOf("2020-01-02");
        Date deliveryDate = Date.valueOf("2020-01-04");
        Delivery delivery = new Delivery(2, 1, orderDate, deliveryDate);
        assertNull(repository.update(delivery));
        var deliveries = repository.findAll();
        assertEquals(1, deliveries.size());
        assertEquals(1, deliveries.get(0).customerId());
        assertEquals(1, deliveries.get(0).orderId());
        assertNotEquals(delivery, deliveries.get(0));
    }
    @Test
    void returnsContents(){
        var deliveryItems = repository.getDeliveryItems(1L);
        DeliveryItem item = deliveryItems.get(0);
        assertEquals(deliveryItems.size(), 1);
        assertEquals(item.productID(), 1);
        assertEquals(item.deliveryId(), 1);
    }
}

