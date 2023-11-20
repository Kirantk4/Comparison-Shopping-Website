package au.edu.rmit.sept.supermarkets.products.models;

public record Notification(int notification_id, int product_id, String notification_description){  
    public int getNotificationID(){
        return notification_id;
    }
}
