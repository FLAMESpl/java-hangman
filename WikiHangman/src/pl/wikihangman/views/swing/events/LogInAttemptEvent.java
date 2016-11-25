package pl.wikihangman.views.swing.events;

import java.util.EventObject;
import pl.wikihangman.models.User;

/**
 * Arguments regarding log in event
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class LogInAttemptEvent extends EventObject {

    private boolean success;
    private String userName;
    private User loggedUser = null;
    
    /**
     * 
     * @param source The object on which the Event initially occurred.
     */
    public LogInAttemptEvent(Object source) {
        super(source);
    }
            
    /**
     * 
     * @return true if authentication was successful, otherwise false
     */
    public boolean getSuccess() {
        return success;
    }
    
    /**
     * 
     * @return name typed by user
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * 
     * @return user matched by authentication service if authentication was
     *      successful, otherwise null
     */
    public User getLoggedUser() {
        return loggedUser;
    }
    
    /**
     * 
     * @param success true if authentication was successful, otherwise false
     * @return this object
     */
    public LogInAttemptEvent setSuccess(boolean success) {
        this.success = success;
        return this;
    }
    
    /**
     * 
     * @param userName name typed by user
     * @return this object
     */
    public LogInAttemptEvent setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    
    /**
     * 
     * @param loggedUser user matched by authentication service if 
     *      authentication successful, otherwise null
     * @return this object
     */
    public LogInAttemptEvent setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
        return this;
    }
}
