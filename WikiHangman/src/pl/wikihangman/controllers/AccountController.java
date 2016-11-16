package pl.wikihangman.controllers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import pl.wikihangman.exception.EntityAlreadyExistsException;
import pl.wikihangman.models.User;

/**
 * {@code AccountController} allows to read and manipulate existing users in
 * database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountController {
    
    private final String DB_PATH = ".\\db.txt";
    
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
        FileInputStream in = new FileInputStream(DB_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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
        
        int id = getUniqueId(userName);
        User user = new User()
                .setId(id)
                .setName(userName)
                .setPassword(password)
                .setPoints(0);
        
        Files.write(Paths.get(DB_PATH), user.databaseEntity().getBytes(), StandardOpenOption.APPEND);
        return user;
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
    private int getUniqueId(String userName) throws
            IOException, EntityAlreadyExistsException {
        
        FileInputStream in = new FileInputStream(DB_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        int id = 1;
        
        while ((line = reader.readLine()) != null) {
            User user = new User().initializeFromTextLine(line);
            int nextId = user.getId();
            String nextName = user.getName();
            
            if (id < nextId) {
                id = nextId;
            }
            
            if (userName.equals(nextName)) {
                throw new EntityAlreadyExistsException("Entity of `" + userName +
                        "` already exists");
            }
        }
        
        return id;
    }
}
