package au.edu.rmit.sept.supermarkets.products.models;

import java.util.HashMap;

//Uses a Hashmap for contents to store both the product and the quantity
public record Cart(int cartID, int customerID, HashMap<Integer,Integer> contents) {
    public int getCartID(){
        return cartID;
    }
    public int getCustomerID(){
        return customerID;
    }
    public HashMap<Integer,Integer> getContents(){
        return contents;
    }
}
