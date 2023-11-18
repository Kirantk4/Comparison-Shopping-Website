package au.edu.rmit.sept.supermarkets.products.models;

import java.sql.Date;

public record Delivery(int orderId, int customerId, Date orderedDate ,Date deliveryDate) {
    public int orderId(){
        return orderId;
    }
}
