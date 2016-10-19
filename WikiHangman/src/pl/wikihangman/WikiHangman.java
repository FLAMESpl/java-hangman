package pl.wikihangman;

import java.io.IOException;
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

    private final static String USERS_DB_PATH = "db.txt";
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
        User user = null;
        boolean exit = false;
        
        while (!exit && user == null) {
            try {
                user = authService.authenticate(arguments.getUser(), arguments.getPassword());
                exit = true;

            } catch (AuthenticationException authenticationException) {
                arguments = argumentsService.read(System.in);
            } catch (IOException ioException) {
                System.exit(-1);
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.exit(-2);
            } catch (NumberFormatException numberFormatException) {
                System.exit(-3);
            }
        }
    }
}
