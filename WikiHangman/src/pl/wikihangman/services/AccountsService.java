package pl.wikihangman.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import pl.wikihangman.exception.EntityAlreadyExistsException;
import pl.wikihangman.models.User;

/**
 * {@code AccountsService} allows to read and manipulate existing users in
 * database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountsService {
    
    private final String dbPath;
    
    /**
     * 
     * @param dbPath path to database file
     */
    public AccountsService(String dbPath) {
        this.dbPath = dbPath;
    }
    
    /**
     * Checks if user of given credentials exists in database.
     * 
     * @param userName user's name
     * @param password user's password
     * @return user object if successful, otherwise null
     * @throws java.io.IOException, NumberFormatException
     */
    public User authenticate(String userName, String password) throws 
            IOException, NumberFormatException {
        
        User nextUser = null;
        BufferedReader reader = databaseBufferedReader();
        String line;
        boolean matched = false;
        
        while((line = reader.readLine()) != null && !matched) {
            nextUser = new User().initializeFromTextLine(line);
            matched = nextUser.authenticate(userName, password);
        } 
        
        return matched ? nextUser : null;
    }
    
    /**
     * Adds user of given credentials to database. Unique id is determined.
     * 
     * @param userName user's name
     * @param password user's password
     * @return created user
     * @throws IOException 
     * @throws pl.wikihangman.exception.EntityAlreadyExistsException 
     */
    public User register(String userName, String password) throws
            IOException, EntityAlreadyExistsException {
        
        int id = getUniqueIdAndCheckName(userName);
        User user = new User()
                .setId(id)
                .setName(userName)
                .setPassword(password)
                .setPoints(0);
        
        String databaseLine = System.lineSeparator() + user.databaseEntity();
        Files.write(Paths.get(dbPath), databaseLine.getBytes(), StandardOpenOption.APPEND);
        return user;
    }
    
    /**
     * Returns all users from the database.
     * 
     * @return All users list
     * @throws IOException, NumberFormatException
     */
    public List<User> getPlayersList() throws IOException, NumberFormatException {
        
        BufferedReader reader = databaseBufferedReader();
        List<User> result = new ArrayList<>();
        String line;
        
        while ((line = reader.readLine()) != null) {
            User user = new User().initializeFromTextLine(line);
            result.add(user);
        }
        
        return result;
    }
    
    /**
     * Finds highest id to determine unique id, additionally asserts if given
     * userName is unique.
     * 
     * @param userName new user's name
     * @return unique id
     * @throws IOException
     * @throws EntityAlreadyExistsException 
     */
    private int getUniqueIdAndCheckName(String userName) throws
            IOException, EntityAlreadyExistsException {
        
        BufferedReader reader = databaseBufferedReader();
        String line;
        int id = 1;
        
        while ((line = reader.readLine()) != null) {
            User user = new User().initializeFromTextLine(line);
            int nextId = user.getId();
            String nextName = user.getName();
            
            if (id < nextId) {
                id = nextId;
            }
            id++;
            
            if (userName.equals(nextName)) {
                throw new EntityAlreadyExistsException("Name", nextName);
            }
        }
        
        return id;
    }
    
    /**
     * Creates {@code BufferedReader} for database file.
     * 
     * @return {@code BufferedReader} for database file
     * @throws IOException 
     */
    private BufferedReader databaseBufferedReader() throws IOException {
        FileInputStream in = new FileInputStream(dbPath);
        return new BufferedReader(new InputStreamReader(in));
    }
}
