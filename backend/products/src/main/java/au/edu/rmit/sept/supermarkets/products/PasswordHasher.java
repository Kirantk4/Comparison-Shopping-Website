package au.edu.rmit.sept.supermarkets.products;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public String Hashpassword(String PasswordtoHash){
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(PasswordtoHash.getBytes());
            byte[] result = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b:result){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm not found");
        }
        return "";
    }
}
