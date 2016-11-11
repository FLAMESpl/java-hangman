package pl.wikihangman.controllers;

import javax.naming.AuthenticationException;
import pl.wikihangman.exceptions.FileException;
import pl.wikihangman.models.AuthenticationService;
import pl.wikihangman.models.User;
import pl.wikihangman.views.ExceptionLogger;
import pl.wikihangman.views.UserInputReader;
import pl.wikihangman.views.UserOptionReader;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthenticationController {
    
    private final String dbPath;
    private final AuthenticationService authenticationService;
    private final static String EXIT_APP_MSG = "Exit application?";
    private final static String[] EXIT_APP_OPTIONS = { "y", "n" };
    private final static String[] AUTH_ARGS_QUESTIONS = { "Username: ", "Password: " };
    
    public AuthenticationController(String dbPath) {
        this.dbPath = dbPath;
        this.authenticationService = new AuthenticationService();
    }
    
    public User authenticate() {
        return authenticate(null);
    }
    
    public User authenticate(String[] applicationArguments) {
        
        ExceptionLogger logger = new ExceptionLogger(System.err);
        User user = null;
        
        try {
            user = authenticationService.authenticate(applicationArguments[0], applicationArguments[1], dbPath);
            
        } catch(AuthenticationException | IndexOutOfBoundsException authFailedException) {
            logger.log(authFailedException);
            user = repeatUserInput(logger);
            
        } catch(FileException fileException) {
            logger.log(fileException);
            
        }
        return user;
    }
    
    private User repeatUserInput(ExceptionLogger logger) {
        
        User user = null;
        UserOptionReader optionReader = new UserOptionReader(
                System.out, System.in, EXIT_APP_OPTIONS, EXIT_APP_MSG);
        UserInputReader inputReader = new UserInputReader();

        while (optionReader.read() == 1) {
            try {
                String[] authenticationArguments = inputReader.read(AUTH_ARGS_QUESTIONS);
                user = authenticationService.authenticate(authenticationArguments[0], 
                        authenticationArguments[1], dbPath);
                break;
            } catch(AuthenticationException authenticationException) {
                logger.log(authenticationException);
            } catch(FileException fileException) {
                logger.log(fileException);
                break;
            }
        }
        
        return user;
    }
}
