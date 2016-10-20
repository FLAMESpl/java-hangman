package pl.wikihangman.core.authentication;

/**
 * Entity of single user from database
 * 
 * @author lszafirski
 */
public class User {
    
    /**
     * User's id cannot be updated.
     */
    final int id;
    String name;
    String password;
    
    /**
     * 
     * @param id        Unique user's id
     * @param name      User's name
     * @param password  User's password
     */
    public User(int id, String name, String password) {
        
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    /**
     * 
     * @return User's id.
     */
    public final int getId() {
        return id;
    }
    
    /**
     * 
     * @return  User's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return User's password.
     */
    public String getPassword() {
        return password;
    }
    
}
