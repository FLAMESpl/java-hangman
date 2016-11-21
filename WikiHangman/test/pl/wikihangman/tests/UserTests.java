package pl.wikihangman.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import pl.wikihangman.models.User;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UserTests {

    @Test
    public void authenticateSuccessfullyWhenValidCredentialsProvided() {
     
        User user = new User()
                .setName("alfa")
                .setPassword("omega");
        
        boolean success = user.authenticate("alfa", "omega");
        assertTrue("Authentication on valid credentials was unsuccessfull", success);
    }
    
    @Test
    public void authenticateUnsuccessfullyWhenInvalidCredentialsProvided() {
        
        User user = new User()
                .setName("alfa")
                .setPassword("omega");
        
        boolean success = user.authenticate("alfa", "delta");
        assertFalse("Authentication on invalid credentials was successfull", success);
        success = user.authenticate("gamma", "omega");
        assertFalse("Authentication on valid credentials was successfull", success);
        success = user.authenticate("gamma", "delta");
        assertFalse("Authentication on valid credentials was successfull", success);
    }
}
