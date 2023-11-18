package au.edu.rmit.sept.supermarkets.products.services;

import au.edu.rmit.sept.supermarkets.products.models.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> getCarts();
    public Cart getCartByID(int id);
    public Cart getCartByCustomerID(int id);
    public boolean removeItemFromCart(int cartid,int productid);
    public boolean addItemToCart(int cartid,int productid, int quantity);
    public boolean incrementQuantityUp(int cartID, int productID);
    public boolean incrementQuantityDown(int cartID, int productID);
    public boolean setQuantity(int cartID, int productID,int quantity);
    public boolean createCartForCustomer(int customerId);
    public Integer checkout(int cartid);
}

