package pl.wikihangman.server.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import pl.wikihangman.server.models.User;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UserTests {

    @Test
    public void authenticatesCorrectCredentials() {
        //given
        String password = "pass";
        String userName = "user";
        User user = new User()
                .setName(userName)
                .setPassword(password);
        
        //when
        boolean authResult = user.authenticate(userName, password);
        
        //then
        assertTrue("Authentication should pass while correct credentials are provided", authResult);
    }
    
    @Test
    public void doNotAuthenticatesCorrectCredentials() {
        //given
        String password = "pass";
        String userName = "user";
        User user = new User()
                .setName(userName + "Z")
                .setPassword(password);
        
        //when
        boolean authResult = user.authenticate(userName, password);
        
        //then
        assertFalse("Authentication should not pass while incorrect credentials are provided", authResult);
    }
    
    @Test
    public void grantsCorrectAmountOfPoints() {
        //given
        long initialPoints = 100;
        User user = new User().setPoints(initialPoints);
        long[] scoreHistory = new long[10];
        long[] expectedScoreHistory = new long[] { 
            200, 250, 283, 308, 328, 344, 358, 370, 381, 391 };
        
        //when
        for (int i = 0; i < 10; i++) {
            user.score(i + 1);
            scoreHistory[i] = user.getPoints();
        }
        
        //then
        assertArrayEquals("Points have not been granted as expected by formula", 
                expectedScoreHistory, scoreHistory);
    }
}
