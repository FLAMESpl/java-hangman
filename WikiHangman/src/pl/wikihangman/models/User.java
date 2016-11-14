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
     * Initializes object using tokens from database's text line.
     * 
     * @param line complete line from database file
     * @return this object
     */
    public User initializeFromTextLine(String line) throws 
            NumberFormatException, IndexOutOfBoundsException {
        String[] words = line.split(" ");
        id = Integer.parseInt(words[0]);
        name = words[1];
        password = words[2];
        points = Long.parseLong(words[3]);
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
    
    /**
     * Creates line of string containing all user information needed for
     * database entity.
     * 
     * @return database text line
     */
    public String databaseEntity() {
        return String.format("%1$d %2$s %3$s %4$s", id, name, password, points);
    }
}
