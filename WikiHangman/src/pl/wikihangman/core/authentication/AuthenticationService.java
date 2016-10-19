package pl.wikihangman.core.authentication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.naming.AuthenticationException;

/**
 * {@code AuthenticationService} is used to authenticate given user.
 * 
 * <p> If in there is no such an user in database, exception is thrown.
 * 
 * @author Łukasz Szafirski
 */
public class AuthenticationService {
    
    private final String dbPath;
    
    /**
     * @param userDbPath Path to the file containing users database.
     */
    public AuthenticationService(String userDbPath) {
        dbPath = userDbPath;
    }
    
    /**
     * Reads user's database in order to match given argument.
     * 
     * <p> File is expected to be composed in "<id> <userName> <userPassword>" 
     * manner.
     * 
     * <p> If access to the file is unsuccessful {@code IOException} is thrown.
     * If file is opened properly but data format error occurs,
     * {@code IndexOutOfBoundsException} is thrown in case unexpected line end,
     * and {@code NumberFormatException} in case parsing error.
     * If file is opened properly but no user and password were matched
     * {@code AuthenticationException} is thrown.
     * 
     * @param user      user's login
     * @param password  user's password
     * @return View of authenticated user
     * @throws AuthenticationException 
     * @throws IOException
     * @throws IndexOutOfBoundsException
     */
    public User authenticate(String user, String password) throws NumberFormatException,
            AuthenticationException, IOException, IndexOutOfBoundsException {
        
        FileInputStream in = new FileInputStream(dbPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean matched = false;
        String line;
        Integer id = -1;
        
        while ((line = reader.readLine()) != null && !matched) {
            String[] words = line.split(" ");

            if (words[1].equals(user) && words[2].equals(password)) {
                matched = true;
                id = Integer.parseInt(words[0]);
            }
        }
        
        if (!matched) {
            throw new AuthenticationException("Invalid credentials");
        }
        
        return new User(id, line, password);
    }  
}
