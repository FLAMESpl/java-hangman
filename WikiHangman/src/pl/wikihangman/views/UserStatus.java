package pl.wikihangman.views;

import java.io.PrintStream;
import pl.wikihangman.models.User;

/**
 * Displays status of current user.
 * 
 * @author Łukasz Szafirski
 */
public class UserStatus {
    
    private final User user;
    
    /**
     * 
     * @param user user whose status will be printed
     */
    public UserStatus(User user) {
        this.user = user;
    }
    
    /**
     * Displays current user's status.
     * 
     * <p> If user is null error message is showed, otherwise user's name is 
     * printed.
     * 
     * @param stream target print stream
     */
    public void display(PrintStream stream) {
        if (user == null) {
            stream.println("Logging failed.");
        }
        else {
            stream.println("Logged as: " + user.getName());
        }
    }
}
