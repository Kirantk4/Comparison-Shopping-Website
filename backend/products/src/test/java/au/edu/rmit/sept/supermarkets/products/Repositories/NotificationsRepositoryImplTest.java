package au.edu.rmit.sept.supermarkets.products.Repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import au.edu.rmit.sept.supermarkets.products.models.Notification;
import au.edu.rmit.sept.supermarkets.products.repositories.NotificationsReopsitory;
import au.edu.rmit.sept.supermarkets.products.repositories.NotificationsRepositoryImpl;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class NotificationsRepositoryImplTest {
    @Autowired
    Flyway flyway;
    @Autowired
    DataSource source;

    NotificationsReopsitory repository;

    @BeforeEach
    void setup(){
        flyway.migrate();
        repository = new NotificationsRepositoryImpl(source);
    }
    @AfterEach
    void tearDown(){
        flyway.clean();
    }
    
    @Test
    void ReturnsWhenAvailable(){
        var notifications = repository.findAll();
        assertEquals(1,notifications.size());
    }

    @Test
    void insertNotification(){
        Notification notification = new Notification(2, 1, "APPLES STILL ONLY $100");
        Notification returnNotification = repository.insert(notification);
        assertEquals(2,repository.findAll().size());
        assertEquals(returnNotification, notification);
    }

    @Test
    void deleteNotification(){
        assertEquals(1,repository.findAll().size());
        assertTrue(repository.delete(1L));
        assertEquals(0,repository.findAll().size());
    }
    @Test
    void failDeleteNotification(){
        assertFalse(repository.delete(2L));
        assertEquals(1,repository.findAll().size());
    }

    @Test
    void updateNotification(){
        Notification notification = new Notification(1, 1, "updated description");
        Notification returnNotification = repository.update(notification);
        assertEquals(1,repository.findAll().size());
        assertEquals(returnNotification, repository.findAll().iterator().next());
        assertEquals(returnNotification, notification);
    }
}
