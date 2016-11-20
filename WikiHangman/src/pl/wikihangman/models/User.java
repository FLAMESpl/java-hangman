package pl.wikihangman.models;

/**
 * Entity of single user from database
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class User {
    
    int id;
    String name;
    String password;
    long points;
    
    /**
     * 
     * @param id new user's id
     * @return this object
     */
    public User setId(int id) {
        this.id = id;
        return this;
    }
    
    /**
     * 
     * @param name new user's name
     * @return this object
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * 
     * @param password new user's password
     * @return this object
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    
    /**
     * 
     * @param points new user's points
     * @return this object
     */
    public User setPoints(long points) {
        this.points = points;
        return this;
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
     * @return  User's password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * @return  User's score.
     */
    public long getPoints() {
        return points;
    }
    
    /**
     * Checks if given name and passwords matches to ones held in class.
     * 
     * @param name user's name
     * @param password user's password
     * @return 
     */
    public boolean authenticate(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}
