package au.edu.rmit.sept.supermarkets.products.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;
import au.edu.rmit.sept.supermarkets.products.services.ProductService;

@SpringBootTest
public class ProductControllerTest {
    ProductController controller;
    ProductService service;

    @BeforeEach
    void setup() {
        this.service = mock(ProductService.class);
        this.controller = new ProductController(this.service);
    }

    @Test
    void should_returnEmpty_When_noRecords() {
        when(this.service.GetProducts()).thenReturn(new HashSet<>());
        assertEquals(0, this.controller.all().size());
    }
    @Test
    void should_returnEmpty_When_Records() {
        Product returnProd = new Product(1,"test", 1, null, 0);
        var returnSet = new HashSet<Product>();
        returnSet.add(returnProd);
        when(this.service.GetProducts()).thenReturn(returnSet);
        assertEquals(1, this.controller.all().size());
    }
    @Test
    void return_on_insert() {
        Product product = new Product(1,"test",1, null, 0);
        when(this.service.insert(product)).thenReturn(product);
        assertEquals(new ResponseEntity<Product>(product, HttpStatus.CREATED), this.controller.insert(product));
    }
    @Test
    void return_true_delete(){
        when(this.service.delete(1L)).thenReturn(true);
        assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED), this.controller.removeProduct(1L));
    }
    @Test
    void return_false_delete(){
        when(this.service.delete(2L)).thenReturn(false);
        assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST), this.controller.removeProduct(2L));
    }
    @Test
    void return_on_edit() {
        Product product = new Product(1,"test", 1, null, 0);
        when(this.service.update(product)).thenReturn(product);
        assertEquals(new ResponseEntity<Product>(product, HttpStatus.ACCEPTED),  this.controller.update(product));
    }
    @Test
    void return_price_update(){
        when(this.service.updatePrice(1L, 1.00, "Coles")).thenReturn(new ItemPrice(1L, 2.0, "Coles"));
        assertEquals(new ResponseEntity<ItemPrice>(new ItemPrice(1L, 2.0, "Coles"), HttpStatus.ACCEPTED), this.controller.updatePrice(1L, 1.00, "Coles"));
    }
    @Test
    void return_price_insert(){
        when(this.service.insertPrice(1L, 1.00, "Coles")).thenReturn(new ItemPrice(1L, 2.0, "Coles"));
        assertEquals(new ResponseEntity<ItemPrice>(new ItemPrice(1L, 2.0, "Coles"), HttpStatus.CREATED), this.controller.insertPrice(1L, 1.00, "Coles"));
    }
    @Test 
    void search_correct_params(){
        Product product = new Product(1,"test", 1, null, 0);
        var returnSet = new HashSet<Product>();
        returnSet.add(product);
        when(this.service.search("", 0, "", 0.00, 0.00, "", "")).thenReturn(returnSet);
        assertEquals(returnSet, this.controller.search(null, 0, null, 0.00, 0.00, null,null));
    }
    @Test
    void return_product_prices(){
        ItemPrice itemPrice = new ItemPrice(1L, 2.0, "Coles");
        var returnSet = new HashSet<ItemPrice>();
        returnSet.add(itemPrice);
        when(this.service.GetProductPrice(1)).thenReturn(returnSet);
        assertEquals(returnSet, this.controller.getPrice(1));
    }
}
