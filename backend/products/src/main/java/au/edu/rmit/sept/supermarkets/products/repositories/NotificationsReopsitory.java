package au.edu.rmit.sept.supermarkets.products.repositories;

import java.util.List;

import au.edu.rmit.sept.supermarkets.products.models.Notification;


public interface NotificationsReopsitory {

    public List<Notification> findAll();
    public Notification findById(Long id);
    public Notification update(Notification notification);
    public Notification insert(Notification notification);
    public boolean delete(Long id);
}
