package au.edu.rmit.sept.supermarkets.products.models;

public record DeliveryItem(int deliveryId,int productID, String productName,int quantity, double price) {
}