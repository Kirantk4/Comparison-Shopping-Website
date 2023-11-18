package au.edu.rmit.sept.supermarkets.products.Repositories;

import au.edu.rmit.sept.supermarkets.products.models.User;
import au.edu.rmit.sept.supermarkets.products.repositories.UserRepository;
import au.edu.rmit.sept.supermarkets.products.repositories.UserRepositoryImpl;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {
    //no invalid tests as all error should be caught by service
    @Autowired
    Flyway flyway;
    @Autowired
    DataSource source;

    UserRepository repository;

    @BeforeEach
    void Setup(){
        flyway.migrate();
        repository = new UserRepositoryImpl(source);
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
    void createsUserWhenAvailable(){
        //runs a test to ensure that the user is actually present in the database
        assertTrue(repository.createUser("test","test","test","test"));
        boolean userpresent = false;
        for (User user:repository.returnAll()){
            if (user.Name().equals("test")){
                userpresent = true;
                break;
            }
        }
        assertTrue(userpresent);
    }
    @Test
    void deleteuser(){
        //runs a test to ensure the user was actually deleted from the database
        assertTrue(repository.deleteUser(1));
        boolean userdeleted = true;
        for (User user:repository.returnAll()){
            if (user.id() == 1){
                userdeleted = false;
                break;
            }
        }
        assertTrue(userdeleted);
    }
}
