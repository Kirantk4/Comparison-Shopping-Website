package au.edu.rmit.sept.supermarkets.products.repositories;
import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;

import java.util.HashSet;

public interface ProductRepository {
    //Hashset as to avoid duplicates
    public HashSet<Product> returnAll();
    public Product findID(int ID);
    public Product insert(Product product);
    public Product update(Product product);
    public boolean delete(Long id);
    public ItemPrice updatePrice(Long id, double price,String supermarket);
    public ItemPrice insertPrice(Long id, double price,String supermarket);
    public HashSet<Product> search(String name, int category, String supermarket, double upper, double lower, String sortBy, String order);
    public HashSet<Integer> categoryChildren(int id);
    public HashSet<ItemPrice> GetProductPrice(int id);
}
