package au.edu.rmit.sept.supermarkets.products.Integration;

import au.edu.rmit.sept.supermarkets.products.ProductsApplication;
import au.edu.rmit.sept.supermarkets.products.models.Notification;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = ProductsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class NotificationIntegrationTest {
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
    void allNotifications() throws Exception {
        mvc.perform(get("/v1/notifications").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].notification_id", is(1)));
    }

    @Test
    void insertNotification() throws Exception{
        mvc.perform(post("/v1/notifications").content(asJsonString(new Notification(0, 1, "wowzers big sale")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void deleteNotification() throws Exception{
        mvc.perform(delete("/v1/notifications/1")).andExpect(status().isAccepted());
    }
    @Test
    void updateNotification() throws Exception{
        mvc.perform(put("/v1/notifications").content(asJsonString(new Notification(1, 1,"zamn")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
