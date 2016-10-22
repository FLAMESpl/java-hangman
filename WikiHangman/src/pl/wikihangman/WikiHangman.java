package pl.wikihangman;

import javax.naming.AuthenticationException;
import pl.wikihangman.models.ApplicationArguments;
import pl.wikihangman.views.ArgumentsReader;
import pl.wikihangman.exceptions.InvalidArgumentsException;
import pl.wikihangman.models.AuthenticationService;
import pl.wikihangman.exceptions.FileException;
import pl.wikihangman.models.User;
import pl.wikihangman.views.ExceptionLogger;
import pl.wikihangman.views.UserOptionReader;
import pl.wikihangman.views.UserStatus;

/**
 * Entry point for an application.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikiHangman {

    private final static String USERS_DB_PATH = ".\\db.txt";
    private final static String EXIT_APP_MSG = "Exit application?";
    private final static String[] EXIT_APP_OPTIONS = { "y", "n" };
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArgumentsReader argumentsService = new ArgumentsReader();
        ApplicationArguments arguments;
        
        try {
            arguments = argumentsService.extract(args);
            
        } catch(InvalidArgumentsException invalidArguments) {
            arguments = argumentsService.read(System.in);
        }
        
        AuthenticationService authService = new AuthenticationService(USERS_DB_PATH);
        User user;
        user = null;
        boolean exit = false;
        
        while (!exit && user == null) {
            try {
                user = authService.authenticate(arguments.getUser(), arguments.getPassword());
                exit = true;

            } catch (AuthenticationException authenticationException) {
                ExceptionLogger exceptionLogger = new ExceptionLogger(authenticationException);
                exceptionLogger.log(System.err);
                
                int selectedOption = -1;
                UserOptionReader optionReader = new UserOptionReader(
                        System.out, System.in, EXIT_APP_OPTIONS, EXIT_APP_MSG);
                
                while (selectedOption < 0) {
                    selectedOption = optionReader.read();
                    
                    if (selectedOption == 0) {
                        exit = true;
                    }
                }
                
                if (!exit) {
                    arguments = argumentsService.read(System.in);
                }
                
            } catch (FileException fileException) {
                exit = true;
                ExceptionLogger exceptionLogger = new ExceptionLogger(fileException);
                exceptionLogger.log(System.err);
            }
        }
        
        UserStatus status = new UserStatus(authService.getUser());
        status.display(System.out);
    }
}
