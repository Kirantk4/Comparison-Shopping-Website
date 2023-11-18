package au.edu.rmit.sept.supermarkets.products.repositories;

import au.edu.rmit.sept.supermarkets.products.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    DataSource source;
    @Autowired
    public UserRepositoryImpl(DataSource source){this.source = source;}

    @Override
    public List<User> returnAll() {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM customers");
            List<User> Customers = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Customers.add(new User(rs.getInt("customer_id"),rs.getString("customer_name"),rs.getString("customer_email"), rs.getNString("customer_address"),rs.getString("customer_password") ));
            }
            connection.close();
            return Customers;
        } catch(SQLException e){
            throw new RuntimeException("Error In returnAll",e);
        }
    }
    @Override
    public boolean createUser(String Name, String Email, String address, String Password) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO customers (customer_name,customer_email,customer_address,customer_password) VALUES('"+Name+"','"+Email+"','"+address+"','"+Password+"')");
            statement.execute();
            //since id is auto incrementing we dont know it until the user has been added to the database
            int id = 0;
            for(User user:returnAll()){
                if (user.Name().equals(Name)&&user.Email().equals(Email)){
                    id = user.id();
                }
            }
            if (id == 0){
                return false;
            }
            //create a new cart for the customer to use
            statement = connection.prepareStatement("INSERT INTO carts (customer_id) VALUES('"+id+"')");
            statement.execute();
            connection.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in createuser",e);
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE customer_id ='"+id+"'");
            statement.execute();
            connection.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in delete user",e);
        }
    }
}
