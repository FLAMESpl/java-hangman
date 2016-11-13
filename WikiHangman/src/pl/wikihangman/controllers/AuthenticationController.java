package pl.wikihangman.controllers;

import javax.naming.AuthenticationException;
import pl.wikihangman.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.exceptions.FileException;
import pl.wikihangman.views.enums.YesNoEnum;
import pl.wikihangman.services.AuthenticationService;
import pl.wikihangman.models.User;
import pl.wikihangman.views.ExceptionLogger;
import pl.wikihangman.views.UserInputReader;
import pl.wikihangman.views.UserOptionReader;

/**
 * {@code AuthenticationController} is used to validate user's credentials
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthenticationController {
    
    private final String dbPath;
    private final AuthenticationService authenticationService;
    private final ExceptionLogger logger;
    private final static String STOP_LOGIN_MSG = "Stop?";
    private final static String[] CREDENTIALS_QUESTIONS = { "Username: ", "Password: " };
    
    /**
     * 
     * @param dbPath path to users' database file
     */
    public AuthenticationController(String dbPath) {
        this.dbPath = dbPath;
        this.authenticationService = new AuthenticationService();
        this.logger = new ExceptionLogger(System.err);
    }
    
    /**
     * Authenticates user using active database file.
     * 
     * <p> In case of invalid credentials user will be asked to try again,
     * but if file error occurs method exits with null value.
     *
     * @return if successful logged user, otherwise null
     */
    public User authenticate() {
        
        return repeatUserAuthentication();
    }
    
    /**
     * Authenticates user using active database file.
     * 
     * <p> In case of invalid credentials user will be asked to try again,
     * but if file error occurs method exits with null value.
     *
     * @param applicationArguments Before asking user to input credentials,
     *      application arguments are used.
     * @return if successful logged user, otherwise null
     */
    public User authenticate(String[] applicationArguments) {
        
        User user = null;
        
        try {
            user = authenticationService.authenticate(applicationArguments[0], applicationArguments[1], dbPath);
            
        } catch(AuthenticationException authenticationException) {
            logger.log(authenticationException);
            user = repeatUserAuthentication();
            
        } catch(IndexOutOfBoundsException indexOutOfBoundsException) {
            logger.log("Cannot read credentials from application's arguments");
            user = repeatUserAuthentication();
            
        } catch(FileException fileException) {
            logger.log(fileException);
            
        }
        return user;
    }
    
    public void register() {
        
        UserInputReader inputReader = new UserInputReader();
        String[] credentials = inputReader.read(CREDENTIALS_QUESTIONS);
        try {
            authenticationService.register(credentials[0], credentials[1], dbPath);
        } catch(FileException | EntityAlreadyExistsException exception) {
            logger.log(exception);
        }
    }
    
    /**
     * Repeats asking user to input user name and password until he decides to
     * exit application or authentication successes.
     * 
     * @return if successful logged user, otherwise null
     */
    private User repeatUserAuthentication() {
        
        User user = null;
        boolean exit = false;
        UserOptionReader optionReader = new UserOptionReader(YesNoEnum.toStringArray(), STOP_LOGIN_MSG);
        UserInputReader inputReader = new UserInputReader();
        
        int selectedOptionIndex;
        YesNoEnum selectedOption;

        while(!exit) {
            try {
                String[] authenticationArguments = inputReader.read(CREDENTIALS_QUESTIONS);
                user = authenticationService.authenticate(authenticationArguments[0], 
                        authenticationArguments[1], dbPath);
                break;
            } catch(AuthenticationException authenticationException) {
                logger.log(authenticationException);
            } catch(FileException fileException) {
                logger.log(fileException);
                break;
            }
            
            selectedOptionIndex = optionReader.read();
            try {
                selectedOption = YesNoEnum.values()[selectedOptionIndex];
            } catch(IndexOutOfBoundsException ex) {
                continue;
            }
            
            if (selectedOption == YesNoEnum.YES) {
                exit = true;
            }
        }
        
        return user;
    }
}
