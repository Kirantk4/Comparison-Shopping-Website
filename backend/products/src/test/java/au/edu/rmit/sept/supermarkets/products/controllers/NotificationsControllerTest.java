package au.edu.rmit.sept.supermarkets.products.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import au.edu.rmit.sept.supermarkets.products.models.Notification;

import au.edu.rmit.sept.supermarkets.products.services.NotificationsService;

public class NotificationsControllerTest {

    NotificationController controller;
    NotificationsService service;

    @BeforeEach
    void setup() {
    this.service = mock(NotificationsService.class);
    this.controller = new NotificationController(this.service);
    }

    @Test
    void should_returnEmpty_When_noRecords() {
      when(this.service.getNotifications()).thenReturn(new ArrayList<>());
      assertEquals(0, this.controller.all().size());

    }

    @Test
    void should_returnNotifications_When_availableInService() {
        when(this.service.getNotifications())
        .thenReturn(List.of(new Notification(1, 1, "description")));
        assertEquals(1, this.controller.all().size());
    }

    @Test
    void return_on_edit() {
    Notification notification = new Notification(1,1, "edited notification");
    when(this.service.update(notification)).thenReturn(notification);
    assertEquals(new ResponseEntity<Notification>(notification,HttpStatus.ACCEPTED), this.controller.update(notification));
    }

    @Test
    void return_true_delete(){
        when(this.service.delete(1L)).thenReturn(true);
        assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED), this.controller.removeNotification(1L));
    }
    @Test
    void return_false_delete(){
        when(this.service.delete(2L)).thenReturn(false);
        assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST), this.controller.removeNotification(2L));
    }
    @Test
    void return_on_insert() {
    Notification notification = new Notification(1,1, "new notification");
    when(this.service.insert(notification)).thenReturn(notification);
    assertEquals(new ResponseEntity<Notification>(notification,HttpStatus.CREATED), this.controller.insert(notification));
  }
}

