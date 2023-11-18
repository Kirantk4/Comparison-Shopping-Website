package au.edu.rmit.sept.supermarkets.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.supermarkets.products.models.Favourite;
import au.edu.rmit.sept.supermarkets.products.repositories.FavouriteRepository;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    FavouriteRepository repository;
    @Autowired
    public FavouriteServiceImpl(FavouriteRepository repository){this.repository =repository;}
    public List<Favourite> returnAll(){
        return repository.returnAll();
    }
    public List<Favourite> getByID(int id){
        List<Favourite> results = repository.getByID(id);
        if(results.size() == 0){
            // if there are no results for the given id just return null
            return null;
        }
        return results;
    }
    public boolean addFavourite(int custid,int productid){
        return repository.addFavourite(custid,productid);
    }
    public boolean removeFavourite(int custid,int productid){
        return repository.removeFavourite(custid,productid);
    }
}
