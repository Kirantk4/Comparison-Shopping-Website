package au.edu.rmit.sept.supermarkets.products.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.supermarkets.products.models.Notification;
import au.edu.rmit.sept.supermarkets.products.repositories.NotificationsReopsitory;



@Service
public class NotificationsServiceImpl implements NotificationsService {

    NotificationsReopsitory repository;

    @Autowired
    public NotificationsServiceImpl(NotificationsReopsitory repository){this.repository = repository;}

    @Override
    public Collection<Notification> getNotifications() {
        
        return repository.findAll();
    }

    @Override
    public Notification getNotification(long id) {
        
        return repository.findById(id);
    }

    @Override
    public Notification insert(Notification notification) {
        
        return repository.insert(notification);
    }

    @Override
    public Notification update(Notification notification) {
        
        return repository.update(notification);
    }

    @Override
    public  boolean delete(Long id) {
        
        return repository.delete(id);
    }
    
}
