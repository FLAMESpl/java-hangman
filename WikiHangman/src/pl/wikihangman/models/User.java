package pl.wikihangman.models;

/**
 * Entity of single user from database
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class User {
    
    /**
     * User's id cannot be updated.
     */
    final int id;
    String name;
    
    /**
     * 
     * @param id        Unique user's id
     * @param name      User's name
     */
    public User(int id, String name) {
        
        this.id = id;
        this.name = name;
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
    
}
