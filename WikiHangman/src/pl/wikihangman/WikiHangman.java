package pl.wikihangman;

import java.util.Scanner;
import javax.naming.AuthenticationException;
import pl.wikihangman.core.arguments.ApplicationArguments;
import pl.wikihangman.core.arguments.ArgumentsService;
import pl.wikihangman.core.arguments.InvalidArgumentsExceptions;
import pl.wikihangman.core.authentication.AuthenticationService;
import pl.wikihangman.core.authentication.FileException;
import pl.wikihangman.core.authentication.User;

/**
 *
 * @author Łukasz Szafirski
 */
public class WikiHangman {

    private final static String USERS_DB_PATH = ".\\db.txt";
    private final static String EXIT_APP_MSG = "Exit application? [y/n]: ";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArgumentsService argumentsService = new ArgumentsService();
        ApplicationArguments arguments;
        
        try {
            arguments = argumentsService.extract(args);
            
        } catch(InvalidArgumentsExceptions invalidArguments) {
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
                System.out.println(authenticationException.getMessage());
                
                boolean userInputValid = false;
                Scanner reader = new Scanner(System.in);
                
                while (!userInputValid) {
                    System.out.print(EXIT_APP_MSG);
                    
                    String userTextInput = reader.next();
                    arguments = argumentsService.read(System.in);
                    System.out.println();
                    
                    if (userTextInput.equals("y")) {
                        userInputValid = true;
                        exit = true;
                    }
                    else if (userTextInput.equals("n")) {
                        userInputValid = true;
                    }
                }
                
            } catch (FileException fileException) {
                exit = true;
            }
        }
        
        if (user == null) {
            System.out.println("Logging failed...");
        } else {
            System.out.println("Logged as " + user.getName());
        }
    }
}
