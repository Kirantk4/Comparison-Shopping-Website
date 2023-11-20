package au.edu.rmit.sept.supermarkets.products.services;

import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;

import java.util.HashSet;

public interface ProductService {
    //hashset as to avoid duplicates
    public HashSet<Product> GetProducts();
    public Product GetProduct(int id);
    public HashSet<ItemPrice> GetProductPrice(int id);
    public Product insert(Product product);
    public Product update(Product product);
    public boolean delete(Long id);
    public ItemPrice updatePrice(Long id, double price,String supermarket);
    public ItemPrice insertPrice(Long id, double price,String supermarket);
    public HashSet<Product> search(String name, int category, String supermarket, double priceUpper,
            double priceLower, String sortBy, String sortOrder);
}
