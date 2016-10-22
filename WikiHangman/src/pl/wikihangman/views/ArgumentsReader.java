package pl.wikihangman.views;

import pl.wikihangman.exceptions.InvalidArgumentsException;
import java.io.InputStream;
import java.util.Scanner;
import pl.wikihangman.models.ApplicationArguments;

/**
 * {@code ArgumentsReader} is responsible for extracting arguments from
 * command-line. In case argument extraction failed, user input might be read.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ArgumentsReader {
    
    /**
     * Message printed when asking user for his name
     */
    private final String ASK_USER_NAME_MSG = "Username: ";
    
    /**
     * Message printed when asking user for his password
     */
    private final String ASK_USER_PASSWORD_MSG = "Password: ";
    
    /**
     * Extracts arguments from arguments array in order: user, password
     * 
     * @param arguments command-line arguments to be processed
     * @return Set of extracted arguments
     * @throws InvalidArgumentsException when there are less then two arguments
     */
    public ApplicationArguments extract(String [] arguments) throws InvalidArgumentsException {
        
        if (arguments.length < 2) {
            throw new InvalidArgumentsException("Invalid number of arguments. Must be 2");
        }
        
        return new ApplicationArguments(arguments[0], arguments[1]);
    }
    
    /**
     * Reads application arguments from given stream
     * 
     * @param input Stream to be read from
     * @return Set of arguments
     */
    public ApplicationArguments read(InputStream input) {
        
        String user, password;
        Scanner reader = new Scanner(input);
        
        System.out.print(ASK_USER_NAME_MSG);
        user = reader.next();
        System.out.println();
        System.out.print(ASK_USER_PASSWORD_MSG);
        password = reader.next();
        System.out.println();
        
        return new ApplicationArguments(user, password);
    }
    
}
