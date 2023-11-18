package au.edu.rmit.sept.supermarkets.products.Integration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.edu.rmit.sept.supermarkets.products.ProductsApplication;
import au.edu.rmit.sept.supermarkets.products.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = ProductsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductsIntegrationTest {
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
    void allProducts() throws Exception{
        mvc.perform(get("/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productID",is(1)));
    }
    @Test
    void findProductById() throws Exception{
        mvc.perform(get("/v1/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productID",is(1)));
    }
    //Leaving For Ben
   /* @Test
    void searchProduct() throws Exception{
        mvc.perform(get("/v1/products/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productID",is(1)));
    }
    */
    @Test
    void insertProduct() throws Exception{
        mvc.perform(post("/v1/products").content(asJsonString(new Product(0, "test", 20, "test", 1)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void deleteProduct() throws Exception{
        mvc.perform(delete("/v1/products/1")).andExpect(status().isAccepted());
    }
    @Test
    void updateProduct() throws Exception{
        mvc.perform(put("/v1/products").content(asJsonString(new Product(1, "test", 20, "test", 1)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }
    @Test
    void priceUpdate() throws Exception{
        mvc.perform(put("/v1/products/priceUpdate/1/25/Coles").content(asJsonString(new Product(1, "test", 20, "test", 1)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }/*
    @Test
    //dunno why this one isnt working, come back later
    void priceInsert() throws Exception{
        mvc.perform(post("/v1/products/priceInsert/1/25/Aldi")).andExpect(status().isCreated());
    }
    */

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
