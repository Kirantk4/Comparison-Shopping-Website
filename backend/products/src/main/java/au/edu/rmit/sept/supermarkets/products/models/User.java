package au.edu.rmit.sept.supermarkets.products.models;

public record User(int id, String Name, String Email, String Address, String Password) {
    @Override
    public String Password() {
        return Password;
    }

    @Override
    public int id() {
        return id;
    }
    public String Email(){return Email;}
}
