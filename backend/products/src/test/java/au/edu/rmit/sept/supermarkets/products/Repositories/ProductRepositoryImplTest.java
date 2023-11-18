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

import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;
import au.edu.rmit.sept.supermarkets.products.repositories.ProductRepository;
import au.edu.rmit.sept.supermarkets.products.repositories.ProductRepositoryImpl;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductRepositoryImplTest {
    @Autowired
    Flyway flyway;
    @Autowired
    DataSource source;

    ProductRepository repository;
    @BeforeEach
    void Setup(){
        flyway.migrate();
        repository = new ProductRepositoryImpl(source);
    }
    @AfterEach
    void tearDown() {
        flyway.clean();
    }

    @Test
    void ReturnsWhenAvailable(){
        var products = repository.returnAll();
        assertEquals(1,products.size());
        assertEquals("Apples", products.iterator().next().productName());
    }
    @Test
    void insertProduct(){
        Product product = new Product(2, "Oranges", 1, "kg", 1);
        Product returnProduct = repository.insert(product);
        assertEquals(2,repository.returnAll().size());
        assertEquals(returnProduct, product);
    }
    @Test
    void deleteProduct(){
        assertEquals(1,repository.returnAll().size());
        assertTrue(repository.delete(1L));
        assertEquals(0,repository.returnAll().size());
    }
    @Test
    void failDeleteProduct(){
        assertFalse(repository.delete(2L));
        assertEquals(1,repository.returnAll().size());
    }
    @Test
    void updateProduct(){
        Product product = new Product(1, "Oranges", 1, "kg", 1);
        Product returnProduct = repository.update(product);
        assertEquals(1,repository.returnAll().size());
        assertEquals(returnProduct, repository.returnAll().iterator().next());
        assertEquals(returnProduct, product);
    }
    @Test
    void priceUpdate(){
        assertEquals(1,repository.returnAll().size());
        ItemPrice price = repository.updatePrice(1L, 20.0, "Coles");
        assertEquals(1,repository.returnAll().size());
        assertEquals(20.0, price.price());
        assertEquals("Coles", price.supermarket());
    }
    @Test
    void search_name(){
        String name = "Apples";
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.search(name, 0, null, 0.0, 0.0, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void search_category(){
        int category = 1;
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.search(null, category, null, 0.0, 0.0, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void search_supermarket(){
        String supermarket = "Coles";
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.search(null, 0, supermarket, 0.0, 0.0, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void search_priceUpper(){
        double priceUpper = 200.0;
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.search(null, 0, null, priceUpper, 0.0, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void search_priceLower(){
        double priceLower = 10.0;
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.search(null, 0, null, 0.0, priceLower, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void search_priceLower_priceUpper(){
        double priceUpper = 200.0;
        double priceLower = 10.0;
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.search(null, 0, null, priceUpper, priceLower, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void search_all_params(){
        String name = "Apples";
        int category = 1;
        String supermarket = "Coles";
        double priceUpper = 200.0;
        double priceLower = 10.0;
        Product product = new Product(1,"Apples", 1, "kg", 1);
        repository.insert(product);
        assertEquals(product, repository.search(name, category, supermarket, priceUpper, priceLower, "DEFAULT", "ASC").iterator().next());
    }
    @Test
    void findID(){
        Product product = new Product(1,"Apples", 1, "kg", 1);
        assertEquals(product, repository.findID(1));
    }
    @Test
    void findItemPrice(){
        ItemPrice price = new ItemPrice(1L, 100, "Coles");
        assertEquals(price, repository.GetProductPrice(1).iterator().next());
    }
}
