package au.edu.rmit.sept.supermarkets.products.services;

import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;
import au.edu.rmit.sept.supermarkets.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository){this.repository = repository;}

    @Override
    public HashSet<Product> GetProducts() {
        return repository.returnAll();
    }

    @Override
    public Product GetProduct(int id) {
        return repository.findID(id);
    }

    @Override
    public Product insert(Product product) {
        return repository.insert(product);
    }

    @Override
    public Product update(Product product) {
        return repository.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return repository.delete(id);
    }

    @Override
    public ItemPrice updatePrice(Long id, double price, String supermarket) {
        return repository.updatePrice(id, price, supermarket);
    }

    @Override
    public ItemPrice insertPrice(Long id, double price, String supermarket) {
        return repository.insertPrice(id, price, supermarket);
    }

    @Override
    public HashSet<Product> search(String name, int category, String supermarket, double priceUpper,
            double priceLower, String sortBy, String sortOrder) {
        if (name.isEmpty()){
            name = null;
        }
        if (supermarket.isEmpty()){
            supermarket = null;
        }
        if (sortBy.isEmpty()){
            sortBy = "DEFAULT";
        }
        if (sortOrder.isEmpty()){
            sortOrder = "ASC";
        }
        return repository.search(name, category, supermarket, priceUpper, priceLower, sortBy, sortOrder);
    }

    @Override
    public HashSet<ItemPrice> GetProductPrice(int id) {
        Product product = repository.findID(id);
        if (product == null){
            return null;
        }
        return repository.GetProductPrice(id);
    }
}
