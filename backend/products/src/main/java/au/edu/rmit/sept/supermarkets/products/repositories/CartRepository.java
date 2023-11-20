package au.edu.rmit.sept.supermarkets.products.repositories;

import au.edu.rmit.sept.supermarkets.products.models.Cart;
import java.util.List;

public interface CartRepository {
    public List<Cart> returnAll();
    public boolean removeitemfromcart(int cartID,int productID);
    public boolean addItemToCart(int cartID, int productID, int quantity);
    public boolean setQuantity(int cartID, int productID,int quantity);
    public Integer checkout(int cartid);
}
