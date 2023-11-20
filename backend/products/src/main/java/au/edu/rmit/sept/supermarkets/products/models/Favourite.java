package au.edu.rmit.sept.supermarkets.products.models;

public record Favourite(int customerid,int productid) {
    public int productid(){
        return productid;
    }
        public int customerid(){
        return customerid;
    }
}
