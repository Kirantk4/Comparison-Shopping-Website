package au.edu.rmit.sept.supermarkets.products.services;

import au.edu.rmit.sept.supermarkets.products.models.Cart;
import au.edu.rmit.sept.supermarkets.products.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    CartRepository repository;
    @Autowired
    public CartServiceImpl(CartRepository repository){this.repository = repository;}
    public List<Cart> getCarts(){
        return repository.returnAll();
    }


    public Cart getCartByID(int id){
        for(Cart cart: repository.returnAll()){
            if (cart.getCartID() == id){
                return cart;
            }
        }
        return null;
    }
    public Cart getCartByCustomerID(int id){
        for(Cart cart: repository.returnAll()){
            if (cart.getCustomerID() == id){
                return cart;
            }
        }
        return null;
    }

    @Override
    public boolean removeItemFromCart(int cartid, int productid) {
        //ensures the item is actually in the cart before trying to remove it
        for(Cart cart: repository.returnAll()){
            if (cart.getCartID() == cartid){
                if (cart.getContents().containsKey(productid)){
                    return repository.removeitemfromcart(cartid,productid);
                } else return false;
            }
        }
        return false;
    }
    @Override
    public boolean addItemToCart(int cartid, int productid,int quantity) {
        //checks if the cart already contains the product they are trying to add
        if (getCartByID(cartid).getContents().containsKey(productid)){
            return false;
        }
        return repository.addItemToCart(cartid,productid,quantity);
    }

    @Override
    public boolean incrementQuantityUp(int cartID, int productID) {
        //ensures the cart has the product in it
        for(Cart cart:repository.returnAll()){
            if (cart.getCartID() == cartID){
                if (cart.getContents().containsKey(productID)){
                    int quantity = cart.getContents().get(productID) + 1;
                    return repository.setQuantity(cartID,productID,quantity);
                }
            }
        }
        return false;
    }

    @Override
    public boolean incrementQuantityDown(int cartID, int productID) {
        //ensures the cart has the product in it
        for(Cart cart:repository.returnAll()){
            if (cart.getCartID() == cartID){
                if (cart.getContents().containsKey(productID)){
                    int quantity = cart.getContents().get(productID) -1;
                    if (quantity == 0){ // if they are de-incrementing the last product in their cart just remove it
                        return repository.removeitemfromcart(cartID,productID);
                    }
                    return repository.setQuantity(cartID,productID,quantity);
                }
            }
        }
        return false;
    }

    @Override
    public boolean setQuantity(int cartID, int productID, int quantity) {
        //ensures the cart has the product in it
        for(Cart cart:repository.returnAll()){
            if (cart.getCartID() == cartID){
                if (cart.getContents().containsKey(productID)){
                    return repository.setQuantity(cartID,productID,quantity);
                }
            }
        }
        return false;
    }

    @Override
    public boolean createCartForCustomer(int customerId) {
        return true;
    }
    public Integer checkout(int cartid){
        for(Cart cart:repository.returnAll()){
            if (cart.cartID() == cartid){
                if(cart.contents().size() != 0){
                    return repository.checkout(cartid);
                }
                return 0;
            }
        }
        return 0;
    }
}
