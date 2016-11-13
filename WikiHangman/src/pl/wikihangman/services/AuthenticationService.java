package pl.wikihangman.services;

import pl.wikihangman.exceptions.FileException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.naming.AuthenticationException;
import pl.wikihangman.exceptions.EntityAlreadyExistsException;
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
     * Reads user's database in order to match given argument.
     * 
     * <p> File is expected to be composed in "(id) (userName) (userPassword)" 
     * manner.
     * 
     * @param user      user's login
     * @param password  user's password
     * @param dbPath    path to database
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
        
        return new User(id, user, points);
    }
    
    /**
     * Adds user to database with specified credentials.
     * 
     * <p> Unique id is determined based on last record of database.
     * User's name have to be unique.
     * 
     * @param user added user's name
     * @param password added user's password
     * @param dbPath path to the users' database
     * @throws FileException in case file cannot be opened or data format is 
     *      incorrect
     * @throws EntityAlreadyExistsException when given user's name is not unique
     */
    public void register(String user, String password, String dbPath) throws 
            FileException, EntityAlreadyExistsException {
        
        int id;
        
        try (FileInputStream in = new FileInputStream(dbPath)) {
            id = getUniqueId(user, in);
            
        } catch(IOException | NumberFormatException fileExceptionCause) {
            throw new FileException("Error occured while reading database file", fileExceptionCause);
        }
        
        try {
            String newLine = String.format("%n%1$d %2$s %3$s 0", id, user, password);
            Files.write(Paths.get(dbPath), newLine.getBytes(), StandardOpenOption.APPEND);
        } catch(IOException fileExceptionCause) {
            throw new FileException("Error occured while appending to database file", fileExceptionCause);
        }
    }
    
    /**
     * Reads database in order to get unique id and asserts if given user's name
     * is unique.
     * 
     * @param in database file stream
     * @return unique id
     * @throws IOException, NumberFormatException, EntityAlreadyExistsException
     */
    private int getUniqueId(String user, FileInputStream in) throws 
            IOException, NumberFormatException, EntityAlreadyExistsException {
        
        int id = 1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            if (user.equals(words[1])) {
                throw new EntityAlreadyExistsException(
                        "User of name " + user + " already exists in database");
            }
            id = Integer.parseInt(words[0]);
        }
        return id;
    }
}
