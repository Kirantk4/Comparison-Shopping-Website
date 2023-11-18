package au.edu.rmit.sept.supermarkets.products.Integration;

import au.edu.rmit.sept.supermarkets.products.ProductsApplication;
import au.edu.rmit.sept.supermarkets.products.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = ProductsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserIntegrationTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    Flyway flyway;

    @BeforeEach
    public void setup() {
        flyway.migrate();
    }

    @AfterEach
    public void teardown() {
        flyway.clean();
    }

    @Test
    void allUsers() throws Exception{
        mvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id",is(1)));
    }
    @Test
    void getUserByID() throws Exception{
        mvc.perform(get("/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)));
    }
    @Test
    void getUserByEmail() throws Exception{
        mvc.perform(get("/v1/users/email/J.D@email.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)));
    }
    @Test
    void deleteUser() throws Exception{
        mvc.perform(get("/v1/users/delete/1")).andExpect(status().isOk());
    }
    @Test
    void createUser() throws Exception{
        mvc.perform(post("/v1/users/create").content(asJsonString(new User(0, "test doe", "nah","test","test")))
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void loginUser() throws Exception{
        mvc.perform(post("/v1/users/login").content(asJsonString(new User(0, null, "J.D@email.com","test","test")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
