package au.edu.rmit.sept.supermarkets.products;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SQLDateGenerator{
    //had to make this class because date generation was such a bloody pain and took up way too much space in the method
    //screw sql dates 
    public static java.sql.Date today(){
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String today = localDate.format(formatter);
            try{
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(today);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
                return sqlDate;
            }catch(Exception e){
                throw new RuntimeException("error in date generation",e);
            }
    }
    public static java.sql.Date estDate(){
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate.plusDays(5);
            String estdelivery = localDate.format(formatter);
            try{
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(estdelivery);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
                return sqlDate;
            }catch(Exception e){
                throw new RuntimeException("error in date generation",e);
            }
    }
}