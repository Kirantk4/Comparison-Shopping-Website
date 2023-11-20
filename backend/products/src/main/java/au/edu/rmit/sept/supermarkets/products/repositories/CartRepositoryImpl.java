package au.edu.rmit.sept.supermarkets.products.repositories;

import au.edu.rmit.sept.supermarkets.products.SQLDateGenerator;
import au.edu.rmit.sept.supermarkets.products.models.Cart;
import au.edu.rmit.sept.supermarkets.products.models.Delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.management.RuntimeErrorException;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Repository
public class CartRepositoryImpl implements CartRepository{
    DataSource source;
    @Autowired
    public CartRepositoryImpl(DataSource source){this.source = source;}
    @Override
    public List<Cart> returnAll() {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM carts");
            List<Cart> carts = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                //runs a query to find the relevant cart contents for each cart
                HashMap<Integer,Integer> contents = new HashMap<>();
                int cart_id = rs.getInt("cart_id");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM cart_contents WHERE cart_id ='"+ cart_id+"'");
                ResultSet cartcontents = statement.executeQuery();
                while (cartcontents.next()){
                    contents.put(cartcontents.getInt("product_id"),cartcontents.getInt("quantity"));
                }
                carts.add(new Cart(cart_id,rs.getInt("customer_id"),contents));
            }
            connection.close();
            return carts;
        } catch(SQLException e){
            throw new RuntimeException("Error In returnAll",e);
        }
    }
    @Override
    public boolean removeitemfromcart(int cartID, int productID) {
        try {
            Connection connection = this.source.getConnection();
            //currently untestable so need to come back to ensure this works
            PreparedStatement statement = connection.prepareStatement("DELETE FROM cart_contents WHERE cart_id ='"+cartID+"' AND product_id ='"+productID+"'");
            statement.execute();
            connection.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in RemoveItem",e);
        }
    }
    public boolean addItemToCart(int cartID, int productID, int quantity) {
        try {
            Connection connection = this.source.getConnection();
            //currently untestable so need to come back to ensure this works
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cart_contents VALUES('"+productID+"','"+cartID+"','"+quantity+"')");
            statement.execute();
            connection.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in addItem",e);
        }
    }

    @Override
    public boolean setQuantity(int cartID, int productID, int quantity) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE cart_contents SET quantity ='"+quantity+"' WHERE cart_id ='"+cartID+"' AND product_id ='"+productID+"'");
            statement.execute();
            connection.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in setQuantity",e);
        }
    }
    public Integer checkout(int cartid){
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT customer_id FROM carts WHERE cart_id ='"+ cartid+"'");
            ResultSet result = statement.executeQuery();
            result.next();
            int custid = result.getInt("customer_id");

            statement = connection.prepareStatement("INSERT INTO deliveries (customer_id, ordered_date, delivery_date) VALUES (?,?,?)",statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, custid);
            statement.setDate(2,SQLDateGenerator.today());
            statement.setDate(3,SQLDateGenerator.estDate());
            statement.execute();
            
            int orderid;

            try(ResultSet key = statement.getGeneratedKeys()){
                key.next();
                orderid = key.getInt("order_id");
            }    catch (Exception e) {
                throw new RuntimeException("Error in checkout, cant get generated keys",e);
            }
            statement = connection.prepareStatement("SELECT * FROM cart_contents WHERE cart_id ='"+cartid+"'");
            ResultSet cartcontents = statement.executeQuery();
            while(cartcontents.next()){
                statement = connection.prepareStatement("SELECT * FROM current_prices WHERE product_id ='"+cartcontents.getInt("product_id")+"'");
                ResultSet productprices = statement.executeQuery();
                int lowestprice = 999999999;
                while (productprices.next()){
                    if(productprices.getInt("product_price") <lowestprice){
                        lowestprice = productprices.getInt("product_price");
                    }
                }
                statement = connection.prepareStatement("INSERT INTO delivery_contents VALUES('"+orderid+"','"+cartcontents.getInt("product_id")+"','"+cartcontents.getInt("quantity")+"','"+lowestprice+"')");
                statement.execute();
            }
            statement = connection.prepareStatement("DELETE FROM cart_contents WHERE cart_id ='"+cartid+"'");
            return orderid;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in setQuantity",e);
        }
    }
}
