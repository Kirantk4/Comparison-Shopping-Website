package au.edu.rmit.sept.supermarkets.products.services;

import java.util.Collection;

import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.models.DeliveryItem;

public interface DeliveryService {
    public Collection<Delivery> getDeliveries();
    public Delivery insert(Delivery delivery);
    public Delivery update(Delivery delivery);
    public boolean delete(Long id);
    public Collection<DeliveryItem> getDeliveryItems(Long id);
}