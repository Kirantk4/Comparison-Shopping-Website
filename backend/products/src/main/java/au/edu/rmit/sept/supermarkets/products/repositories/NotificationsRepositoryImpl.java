package au.edu.rmit.sept.supermarkets.products.repositories;

import au.edu.rmit.sept.supermarkets.products.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class NotificationsRepositoryImpl implements NotificationsReopsitory{
    private final DataSource source;
    @Autowired
    public NotificationsRepositoryImpl(DataSource source){this.source = source;}

    @Override
    public List<Notification> findAll() {
        
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM notifications");
            List<Notification> notifications = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Notification n = new Notification(rs.getInt("notification_id"), rs.getInt("product_id"), rs.getString("notification_description"));
                notifications.add(n);
            }
            connection.close();
            return notifications;
        } catch(SQLException e){
            throw new RuntimeException("Error In findAll",e);
        }
    };
    
    @Override
    public Notification findById(Long id) {
        
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM notifications WHERE Notification_ID ='"+ id + "'");
            ResultSet rs = stm.executeQuery();
            Notification notification = new Notification(rs.getInt("notification_id"),rs.getInt("product_id"),rs.getString("notification_description"));
            connection.close();
            return notification;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in findByID",e);
        }
    }
    
    @Override
    public Notification update(Notification notification) {
        
         
            try {
                Connection connection = this.source.getConnection();
                PreparedStatement stm = connection.prepareStatement("UPDATE notifications SET notification_description = ? WHERE notification_id = ?");
                stm.setString(1, "updated description");
                stm.setInt(2, notification.notification_id());
                int row = stm.executeUpdate();
                connection.close();
                if (row == 0){
                    return null;
                }
                return notification;
            }
            catch (SQLException e){
                throw new RuntimeException("Error in update",e);
            }
        
    }
    @Override
    public Notification insert(Notification notification) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO notifications (product_id, notification_description) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, notification.product_id());
            stm.setString(2, notification.notification_description());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()){
                Notification returnNotification = new Notification(rs.getInt(1), notification.product_id() ,notification.notification_description());
                connection.close();
                return returnNotification;
            }
            else{
                connection.close();
                throw new SQLException("Creating Product Failed, No ID");
            } 
        }
        catch (SQLException e){
            throw new RuntimeException("Error in insert",e);
        }
    }
    @Override
    public boolean delete(Long id) {
        try {
            String statement1 = "DELETE FROM notifications_associations WHERE notification_id = " + id + " ;";
            statement1 += "DELETE FROM notifications WHERE notification_id = " + id + " ;";
            Connection connection = this.source.getConnection();
            PreparedStatement stm4 = connection.prepareStatement(statement1);
            int row = stm4.executeUpdate();
            if (row == 0){
                connection.close();
                return false;
            }
            else{
                connection.close();
                return true;
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error in delete",e);
        }
    }

    
}






