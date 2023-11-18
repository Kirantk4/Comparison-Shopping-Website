package au.edu.rmit.sept.supermarkets.products.Integration;

import au.edu.rmit.sept.supermarkets.products.ProductsApplication;
import au.edu.rmit.sept.supermarkets.products.models.iteminfo;
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
public class CartIntegrationTest {
        @Autowired
        MockMvc mvc;
        @Autowired
        Flyway flyway;

        @BeforeEach
        public void setup(){
            flyway.migrate();
        }
        @AfterEach
        public void teardown(){
            flyway.clean();
        }

        @Test
        void allCarts() throws Exception{
            mvc.perform(get("/v1/carts").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].cartID",is(1)));
        }
        @Test
        void getCartByID() throws Exception{
            mvc.perform(get("/v1/carts/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.cartID",is(1)));
        }
        @Test
        void getCartByCustomerID() throws Exception{
            mvc.perform(get("/v1/carts/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cartID",is(1)));
    }
        @Test
        void removeProduct() throws Exception{
            mvc.perform(post("/v1/carts/delete").content(asJsonString(new iteminfo(1, 1, 1)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        @Test
        void addProduct() throws Exception{
            mvc.perform(post("/v1/carts/add").content(asJsonString(new iteminfo(2, 1, 1)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
        @Test
        void incrementProduct() throws Exception{
            mvc.perform(post("/v1/carts/increment").content(asJsonString(new iteminfo(1, 1, 0)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        @Test
        void decrementProduct() throws Exception{
            mvc.perform(post("/v1/carts/decrement").content(asJsonString(new iteminfo(1, 1, 0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
        @Test
        void setQuantity() throws Exception{
            mvc.perform(post("/v1/carts/set").content(asJsonString(new iteminfo(1, 1, 10)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
        @Test
        void CheckoutTest() throws Exception{
            mvc.perform(get("/v1/carts/checkout/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$",is(2)));
        }
        public static String asJsonString(final Object obj) {
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

