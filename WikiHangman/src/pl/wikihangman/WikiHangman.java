package pl.wikihangman;

import javax.naming.AuthenticationException;
import pl.wikihangman.core.arguments.ApplicationArguments;
import pl.wikihangman.core.arguments.ArgumentsService;
import pl.wikihangman.core.arguments.InvalidArgumentsExceptions;
import pl.wikihangman.core.authentication.AuthenticationService;
import pl.wikihangman.core.authentication.User;

/**
 *
 * @author Łukasz Szafirski
 */
public class WikiHangman {

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
        
        AuthenticationService authService = new AuthenticationService();
        User user = null;
        boolean exit = false;
        
        while (!exit && user == null) {
            try {
                user = authService.authenticate(arguments.getUser(), arguments.getPassword());
                exit = true;

            } catch (AuthenticationException authenticationException) {
                arguments = argumentsService.read(System.in);
            }
        }
    }
}
