package au.edu.rmit.sept.supermarkets.products.repositories;

import java.util.List;

import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.models.DeliveryItem;

public interface DeliveryRepository {
    public List<Delivery> findAll();
    public List<Delivery> findAllId(int customer_id);
    public Delivery findById(Long id);
    public Delivery update(Delivery delivery);
    public Delivery insert(Delivery delivery);
    public boolean delete(Long id);
    public List<DeliveryItem> getDeliveryItems(Long id);
}