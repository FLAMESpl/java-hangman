package pl.wikihangman.core.authentication;

import javax.naming.AuthenticationException;

/**
 * {@code AuthenticationService} is used to authenticate given user.
 * 
 * <p> If in there is no such an user in database, exception is thrown.
 * 
 * @author Łukasz Szafirski
 */
public class AuthenticationService {
    
    /**
     * Reads user's database in order to match given argument.
     * 
     * @param user      user's login
     * @param password  user's password
     * @return View of authenticated user
     * @throws AuthenticationException 
     */
    public User authenticate(String user, String password) throws AuthenticationException {
        
        throw new AuthenticationException("Invalid credentials");
    }
            
}
