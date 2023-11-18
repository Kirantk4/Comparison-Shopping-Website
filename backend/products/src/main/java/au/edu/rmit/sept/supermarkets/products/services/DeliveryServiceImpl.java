package au.edu.rmit.sept.supermarkets.products.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.models.DeliveryItem;
import au.edu.rmit.sept.supermarkets.products.repositories.DeliveryRepository;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    private DeliveryRepository repository;

    public DeliveryServiceImpl(DeliveryRepository repository) {
        this.repository = repository;
    }
    @Override
    public Collection<Delivery> getDeliveries() {
        return repository.findAll();
    }
    @Override
    public Delivery insert(Delivery delivery) {
        return repository.insert(delivery);
    }
    @Override
    public Delivery update(Delivery delivery) {
        return repository.update(delivery);
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id);
    }
    @Override
    public Collection<DeliveryItem> getDeliveryItems(Long id) {
        return repository.getDeliveryItems(id);
    }
}