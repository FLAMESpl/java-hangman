package pl.wikihangman;

import pl.wikihangman.controllers.AuthenticationController;
import pl.wikihangman.models.User;
import pl.wikihangman.views.UserStatus;

/**
 * Entry point for an application.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikiHangman {

    private final static String USERS_DB_PATH = ".\\db.txt";
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AuthenticationController authenticationController = 
                new AuthenticationController(USERS_DB_PATH);
        
        User user = authenticationController.authenticate(args);
        
        UserStatus status = new UserStatus(user);
        status.display(System.out);
    }
}
