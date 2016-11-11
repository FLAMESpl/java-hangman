package pl.wikihangman.controllers;

import javax.naming.AuthenticationException;
import pl.wikihangman.exceptions.FileException;
import pl.wikihangman.models.AuthenticationService;
import pl.wikihangman.models.User;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthenticationController {
    
    private final String dbPath;
    private final AuthenticationService authenticationService;
    
    public AuthenticationController(String dbPath) {
        this.dbPath = dbPath;
        this.authenticationService = new AuthenticationService();
    }
    
    public User authenticate() {
        return authenticate(null);
    }
    
    public User authenticate(String[] applicationArguments) {
        
        try {
            authenticationService.authenticate(applicationArguments[0], applicationArguments[1], dbPath);
        } catch(AuthenticationException,  authenticationException) {
            
        } catch(FileException fileException) {
            
        }
                
    }
}
