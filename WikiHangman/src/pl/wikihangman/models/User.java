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
    long points;
    
    /**
     * 
     * @param id        Unique user's id
     * @param name      User's name
     * @param points    User's points
     */
    public User(int id, String name, long points) {
        
        this.id = id;
        this.name = name;
        this.points = points;
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
     * @return  User's score.
     */
    public long getPoints() {
        return points;
    }
    
}
