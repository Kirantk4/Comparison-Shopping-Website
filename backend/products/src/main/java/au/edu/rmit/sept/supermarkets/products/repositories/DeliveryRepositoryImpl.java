package au.edu.rmit.sept.supermarkets.products.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import au.edu.rmit.sept.supermarkets.products.models.Delivery;
import au.edu.rmit.sept.supermarkets.products.models.DeliveryItem;

@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository {
    private final DataSource source;
    public DeliveryRepositoryImpl(DataSource source) {
        this.source = source;
    }
    @Override
    public List<Delivery> findAll() {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM deliveries");
            List<Delivery> deliveries = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Delivery m = new Delivery(rs.getInt("order_id"), rs.getInt("customer_id"), rs.getDate("ordered_date"), rs.getDate("delivery_date"));
                deliveries.add(m);
            }
            connection.close();
            return deliveries;
        } catch(SQLException e){
            throw new RuntimeException("Error In findAll",e);
        }
    }
    @Override
    public List<Delivery> findAllId(int customer_id) {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM deliveries where customer_id ='" + customer_id + "'");
            List<Delivery> deliveries = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Delivery m = new Delivery(rs.getInt("order_id"), rs.getInt("customer_id"), rs.getDate("ordered_date"), rs.getDate("delivery_date"));
                deliveries.add(m);
            }
            connection.close();
            return deliveries;
        } catch(SQLException e){
            throw new RuntimeException("Error In findAll",e);
        }
    }
    @Override
    public Delivery findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    @Override
    public Delivery update(Delivery delivery) {
        try {
            //Try update delivery
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE deliveries SET customer_id = ?, ordered_date = ?, delivery_date = ? WHERE order_id = ?");
            stm.setInt(1, delivery.customerId());
            stm.setDate(2, delivery.orderedDate());
            stm.setDate(3, delivery.deliveryDate());
            stm.setInt(4, delivery.orderId());
            int row = stm.executeUpdate();
            //If 0 no changes made
            if (row == 0){
                return null;
            }
            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException("Error in update Delivery", e);
        }
    }
    @Override
    public Delivery insert(Delivery delivery) {
        try {
            //Try insert delivery
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO deliveries (customer_id, ordered_date, delivery_date) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, delivery.customerId());
            stm.setDate(2, delivery.orderedDate());
            stm.setDate(3, delivery.deliveryDate());
            int row = stm.executeUpdate();
            //If 0 no changes made
            if (row == 0){
                throw new RuntimeException("Error in Insert created no new delivery");
            }
            ResultSet generatedKeys = stm.getGeneratedKeys();
            //If next then the book id was generated
            if (generatedKeys.next()) {
                return new Delivery(generatedKeys.getInt(1), delivery.customerId(), delivery.orderedDate(), delivery.deliveryDate());
            }
            else {
                throw new SQLException("Creating Delivery Failed, No ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in insert Delivery", e);
        }
    }
    @Override
    public boolean delete(Long id) {
        try {
            //Try delete delivery_contents and delivery parent
            String statement1 = "DELETE FROM delivery_contents WHERE delivery_id = " + id + ";";
            String statement2 = "DELETE FROM deliveries WHERE order_id = " + id;
            Connection connection = this.source.getConnection();
            PreparedStatement stm2 = connection.prepareStatement(statement1 + statement2);
            int row = stm2.executeUpdate();
            //If 0 no changes made
            if (row == 0){
                connection.close();
                return false;
            }
            else{
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in delete Delivery", e);
        }
    }
    @Override
    public List<DeliveryItem> getDeliveryItems(Long id) {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM delivery_contents NATURAL JOIN products WHERE delivery_id = " + id);
            List<DeliveryItem> deliveryItems = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                DeliveryItem m = new DeliveryItem(rs.getInt("delivery_id"), rs.getInt("product_id"), rs.getString("product_name"), rs.getInt("quantity"), rs.getDouble("price"));
                deliveryItems.add(m);
            }
            connection.close();
            return deliveryItems;
        } catch(SQLException e){
            throw new RuntimeException("Error In getDeliveryItems",e);
        }
    }
}