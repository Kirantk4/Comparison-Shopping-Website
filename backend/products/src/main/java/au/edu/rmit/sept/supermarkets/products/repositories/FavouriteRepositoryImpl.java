package au.edu.rmit.sept.supermarkets.products.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.supermarkets.products.models.Favourite;

@Repository
public class FavouriteRepositoryImpl implements FavouriteRepository {

    DataSource source;
    @Autowired
    public FavouriteRepositoryImpl(DataSource source){this.source = source;}

    public List<Favourite> returnAll(){
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM favourites");
            List<Favourite> favourites = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                favourites.add(new Favourite(rs.findColumn("customer_id"), rs.findColumn("product_id") ));
            }
            connection.close();
            return favourites;
        } catch(SQLException e){
            throw new RuntimeException("Error In returnAll",e);
        }
    }

    public List<Favourite> getByID(int id){
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM favourites WHERE customer_id ='"+id+"'");
            List<Favourite> favourites = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                favourites.add(new Favourite(rs.findColumn("customer_id"), rs.findColumn("product_id") ));
            }
            connection.close();
            return favourites;
        } catch(SQLException e){
            throw new RuntimeException("Error In getByID",e);
        }
    }
    public boolean addFavourite(int custid,int productid){
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO favourites VALUES('"+custid+"','"+productid+"')" );
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error In addFavourite",e);
        }
    }
    public boolean removeFavourite(int custid,int productid){
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("Delete FROM favourites WHERE customer_id ='"+custid+"' AND product_id ='"+productid+"'");
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error In removeFavourite",e);
        }
    }
}
