package au.edu.rmit.sept.supermarkets.products;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordHashTest {
    PasswordHasher ph = new PasswordHasher();
    @Test
    void EnsureNoException(){
        ph.Hashpassword("test");
    }
    @Test
    void EnsureConsistentHashing(){
        assertEquals(ph.Hashpassword("Test"),ph.Hashpassword("Test"));
    }
    @Test
    void EnsureDifferentResults(){
        assertNotEquals(ph.Hashpassword("Test"),ph.Hashpassword("Test2"));
    }
    @Test
    void Ensureactuallyhashed(){
        assertNotEquals("test",ph.Hashpassword("test"));
    }
}
