package pl.wikihangman.models;

/**
 * Set of application arguments
 * 
 * @author  Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ApplicationArguments {
    
    private final String user;
    private final String password;
    
    /**
     * 
     * @param user      User's name
     * @param password  User's password
     */
    public ApplicationArguments(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    /**
     * @return Requested user's login
     */
    public final String getUser() {
        return user;
    }
    
    /**
     * @return Requested user's password
     */
    public final String getPassword() {
        return password;
    }
    
    
}
