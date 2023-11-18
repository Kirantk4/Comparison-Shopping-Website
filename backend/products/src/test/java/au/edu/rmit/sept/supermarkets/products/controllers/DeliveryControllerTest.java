package au.edu.rmit.sept.supermarkets.products.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.services.DeliveryService;



public class DeliveryControllerTest {

  DeliveryController controller;
  DeliveryService service;

  @BeforeEach
  void setup() {
    this.service = mock(DeliveryService.class);
    this.controller = new DeliveryController(this.service);
  }

  @Test
  void should_returnEmpty_When_noRecords() {
    when(this.service.getDeliveries()).thenReturn(new ArrayList<>());
    assertEquals(0, this.controller.all().size());
  }

  @Test
  void should_returnDeliveries_When_availableInService() {
    when(this.service.getDeliveries())
        
        .thenReturn(List.of(new Delivery(1,1, Date.valueOf("2023-04-09"),Date.valueOf("2023-04-12"))));
    assertEquals(1, this.controller.all().size());
  }

  @Test
  void return_on_insert() {
    Delivery delivery = new Delivery(1,1, Date.valueOf("2023-04-09"),Date.valueOf("2023-04-12"));
    when(this.service.insert(delivery)).thenReturn(delivery);
    assertEquals(new ResponseEntity<Delivery>(delivery,HttpStatus.CREATED), this.controller.insert(delivery));
  }

  @Test
  void return_on_edit() {
    Delivery delivery = new Delivery(1,1, Date.valueOf("2023-04-09"),Date.valueOf("2023-04-12"));
    when(this.service.update(delivery)).thenReturn(delivery);
    assertEquals(new ResponseEntity<Delivery>(delivery,HttpStatus.ACCEPTED), this.controller.update(delivery));
  }

  @Test
  void delete_called_once() {
    this.controller.removeDelivery(1L);
    verify(this.service, times(1)).delete(1L);
  }

  @Test
  void orderContents() {
    when(this.service.getDeliveryItems(1L)).thenReturn(new ArrayList<>());
    assertEquals(0, this.controller.deliveryItems(1L).size());
  }
}