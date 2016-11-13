package pl.wikihangman.services;

import pl.wikihangman.exceptions.FileException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.naming.AuthenticationException;
import pl.wikihangman.models.User;

/**
 * {@code AuthenticationService} is used to authenticate given user.
 * 
 * <p> If in there is no such an user in database, exception is thrown.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthenticationService {
    
    /**
     * User logged by this service
     */
    private User loggedUser = null;
    
    
    /**
     * 
     * @return user logged by this service
     */
    public User getUser() {
        return loggedUser;
    }
    
    /**
     * Reads user's database in order to match given argument.
     * 
     * <p> File is expected to be composed in "(id) (userName) (userPassword)" 
     * manner.
     * 
     * @param user      user's login
     * @param password  user's password
     * @return Authenticated user class
     * @throws AuthenticationException when either data in file is not properly
     *         formatted or matching pair of user's name and password were not
     *         matched
     * @throws FileException when file cannot be opened
     */
    
    public User authenticate(String user, String password, String dbPath) throws
            AuthenticationException, FileException {
        
        int id = -1;
        long points = 0;
        
        try (FileInputStream in = new FileInputStream(dbPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            boolean matched = false;
            String line;

            while ((line = reader.readLine()) != null && !matched) {
                String[] words = line.split(" ");

                if (words[1].equals(user) && words[2].equals(password)) {
                    matched = true;
                    id = Integer.parseInt(words[0]);
                    points = Long.parseLong(words[3]);
                }
            }

            if (!matched) {
                throw new AuthenticationException("Invalid credentials");
            }
        } catch(IOException | IndexOutOfBoundsException | NumberFormatException fileExceptionCause) {
            throw new FileException("Error occured while reading database file", fileExceptionCause);
        }
        
        loggedUser = new User(id, user, points);
        return loggedUser;
    }  
}
