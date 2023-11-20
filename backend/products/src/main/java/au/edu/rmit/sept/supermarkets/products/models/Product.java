package au.edu.rmit.sept.supermarkets.products.models;

public record Product(int productID,String productName, int size,String unit, int category_id) {
    public int GetproductID(){
        return productID;
    }
}
