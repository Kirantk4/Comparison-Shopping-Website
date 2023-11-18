package au.edu.rmit.sept.supermarkets.products.repositories;

import au.edu.rmit.sept.supermarkets.products.models.ItemPrice;
import au.edu.rmit.sept.supermarkets.products.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;
import javax.sql.DataSource;


@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final DataSource source;
    @Autowired
    public ProductRepositoryImpl(DataSource source){this.source = source;}

    @Override
    public HashSet<Integer> categoryChildren(int id){
        HashSet<Integer> children = new HashSet<>();
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM categories WHERE parent_category_id = " + id);
            ResultSet rs = stm.executeQuery();
            //Base Case
            while(rs.next()){
                //For Each Child Check if it has children and add them
                int child = rs.getInt("category_id");
                children.addAll(categoryChildren(child));
            }
            children.add(id);
            connection.close();
        } catch(SQLException e){
            throw new RuntimeException("Error In categoryChildren",e);
        }
        return children;
    }

    @Override
    public HashSet<Product> returnAll() {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM products");
            HashSet<Product> products = new HashSet<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Product product = new Product(rs.getInt("product_id"),rs.getString("product_name"), rs.getInt("size"),rs.getString("unit"), rs.getInt("category_id"));
                products.add(product);
            }
            connection.close();
            return products;
        } catch(SQLException e){
            throw new RuntimeException("Error In returnAll",e);
        }
    }
    public Product findID(int ID){
        try {
            Connection connection = this.source.getConnection();
            String statement = "SELECT * FROM products WHERE product_id = "+ ID + ";";
            PreparedStatement stm = connection.prepareStatement(statement);
            ResultSet rs = stm.executeQuery();
            rs.next();
            Product product = new Product(rs.getInt("product_id"),rs.getString("product_name"), rs.getInt("size"),rs.getString("unit"), rs.getInt("category_id"));
            connection.close();
            return product;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in findID",e);
        }
    }

    @Override
    public Product insert(Product product) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO products (product_name, size, unit, category_id) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, product.productName());
            stm.setInt(2, product.size());
            stm.setString(3, product.unit());
            stm.setInt(4, product.category_id());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()){
                Product returnProduct = new Product(rs.getInt(1), product.productName(), product.size(), product.unit(), product.category_id());
                connection.close();
                return returnProduct;
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
    public Product update(Product product) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE products SET product_name = ?, size = ?, unit = ?, category_id = ? WHERE product_id = ?");
            stm.setString(1, product.productName());
            stm.setInt(2, product.size());
            stm.setString(3, product.unit());
            stm.setInt(4, product.category_id());
            stm.setInt(5, product.productID());
            int row = stm.executeUpdate();
            connection.close();
            if (row == 0){
                return null;
            }
            return product;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in update",e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Connection connection = this.source.getConnection();
            String statement1 = "DELETE FROM products WHERE product_id = " + id;
            PreparedStatement stm1 = connection.prepareStatement(statement1);
            int row = stm1.executeUpdate();
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

    @Override
    public ItemPrice updatePrice(Long id, double price, String supermarket) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm1 = connection.prepareStatement("SELECT * FROM current_prices WHERE product_id = ? AND super_market = ?");
            stm1.setLong(1, id);
            stm1.setString(2, supermarket);
            ResultSet rs = stm1.executeQuery();
            if (!rs.next()){
                //If price doesn't currentyl exist return null
                connection.close();
                return null;
            }
            else{
                //If Price is the same as the current price return null
                if (rs.getDouble("product_price") == price){
                    connection.close();
                    return null;
                }
                else{
                    PreparedStatement stm2 = connection.prepareStatement("INSERT INTO price_histories (product_id, product_price, super_market,  price_date) VALUES (?,?,?,?)");
                    stm2.setLong(1, id);
                    stm2.setDouble(2, rs.getDouble("product_price"));
                    stm2.setString(3, supermarket);
                    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                    stm2.setDate(4, currentDate);
                    stm2.executeUpdate();
                    //If CHEAPER add notification
                    if (rs.getDouble("product_price") > price){
                        //HERE IS WHERE YOU ADD NOTIFICATIONS
                    }
                }
            }
            PreparedStatement stm4 = connection.prepareStatement("UPDATE current_prices SET product_price = ? WHERE product_id = ? AND super_market = ?");
            stm4.setDouble(1, price);
            stm4.setLong(2, id);
            stm4.setString(3, supermarket);
            int row = stm4.executeUpdate();
            if (row == 0){
                connection.close();
                return null;
            }
            else{
                return new ItemPrice(id, price, supermarket);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error in updatePrice",e);
        }
    }
    @Override
    public ItemPrice insertPrice(Long id, double price,String supermarket){
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO current_prices (product_id, product_price, super_market) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setLong(1, id);
            stm.setDouble(2, price);
            stm.setString(3, supermarket);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()){
                ItemPrice returnPrice = new ItemPrice(rs.getLong(1), rs.getDouble(2), rs.getString(3));
                connection.close();
                return returnPrice;
            }
            else{
                connection.close();
                throw new SQLException("Creating Price Failed, No ID");
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error in insertPrice",e);
        }
    };

    @Override
    public HashSet<Product> search(String name, int category, String supermarket, double upper, double lower, String sortBy ,String sortOrder) {
        try{
            Connection connection = this.source.getConnection();
            String statement = "SELECT * FROM products ";
            Boolean first = true;
            ResultSet rs;
            if (name != null){
                if (first){
                    statement += "WHERE";
                    first = false;
                }
                else{
                    statement += "AND";
                }
                statement += " product_name ILIKE '%" + name + "%' ";
            }
            if (category != 0){
                if (first){
                    statement += "WHERE";
                    first = false;
                }
                else{
                    statement += "AND";
                }
                HashSet<Integer> children = categoryChildren(category);
                statement += " category_id IN (";
                for (int child : children){
                    statement += child + ",";
                }
                statement = statement.substring(0, statement.length() - 1);
                statement += ") ";
            }
            if (supermarket != null){
                if (first){
                    statement += "WHERE";
                    first = false;
                }
                else{
                    statement += "AND";
                }
                statement += " product_id in (SELECT product_id FROM current_prices WHERE super_market ILIKE '%" + supermarket + "%') ";
            }
            if (upper != 0){
                if (first){
                    statement += "WHERE";
                    first = false;
                }
                else{
                    statement += "AND";
                }
                statement += " product_id in (SELECT product_id FROM current_prices WHERE product_price <= " + upper + ") ";
            }
            if (first){
                statement += "WHERE";
                first = false;
            }
            else{
                statement += "AND";
            }
            statement += " product_id in (SELECT product_id FROM current_prices WHERE product_price >= " + lower + ") ";
            if (sortBy.equals("DEFAULT")){
                statement += " ORDER BY product_id";
            }
            else if (sortBy.equals("NAME")){
                statement += " ORDER BY product_name";
            }
            else if (sortBy.equals("PRICE")){
                statement += " ORDER BY product_price";
            }
            else if (sortBy.equals("SUPERMARKET")){
                statement += " ORDER BY super_market";
            }
            else if (sortBy.equals("CATEGORY")){
                statement += " ORDER BY category_id";
            }
            if (sortOrder.equals("ASC")){
                statement += " ASC";
            }
            else if (sortOrder.equals("DESC")){
                statement += " DESC";
            }
            PreparedStatement stm = connection.prepareStatement(statement);
            HashSet<Product> products = new HashSet<>();
            rs = stm.executeQuery();
            while(rs.next()){
                Product product = new Product(rs.getInt("product_id"),rs.getString("product_name"), rs.getInt("size"),rs.getString("unit"), rs.getInt("category_id"));
                products.add(product);
            }
            connection.close();
            return products;
        }
        catch (SQLException e){
            throw new RuntimeException("Error in search",e);
        }

    }

    @Override
    public HashSet<ItemPrice> GetProductPrice(int id) {
        try{
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM current_prices WHERE product_id = " + id);
            HashSet<ItemPrice> prices = new HashSet<>();
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                ItemPrice price = new ItemPrice(rs.getLong("product_id"), rs.getDouble("product_price"), rs.getString("super_market"));
                prices.add(price);
            }
            connection.close();
            return prices;
        } catch(SQLException e){
            throw new RuntimeException("Error In GetProductPrice",e);
        }
    }

}
