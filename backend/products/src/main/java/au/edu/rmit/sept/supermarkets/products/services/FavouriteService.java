package au.edu.rmit.sept.supermarkets.products.services;

import java.util.List;

import au.edu.rmit.sept.supermarkets.products.models.Favourite;

public interface FavouriteService {
    public List<Favourite> returnAll();
    public List<Favourite> getByID(int id);
    public boolean addFavourite(int custid,int productid);
    public boolean removeFavourite(int custid,int productid);
    
}
