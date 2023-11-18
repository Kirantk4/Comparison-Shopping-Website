package au.edu.rmit.sept.supermarkets.products.services;

import java.util.Collection;

import au.edu.rmit.sept.supermarkets.products.models.Notification;


public interface NotificationsService {
    
    public Collection<Notification> getNotifications();
    public Notification getNotification(long id);
    public Notification insert(Notification notification);
    public Notification update(Notification notification);
    public boolean delete(Long id);
}
