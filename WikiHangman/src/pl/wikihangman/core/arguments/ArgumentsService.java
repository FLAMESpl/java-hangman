package pl.wikihangman.core.arguments;

import java.io.InputStream;

/**
 * {@code ArgumentsService} is responsible for extracting arguments from
 * command-line. In case argument extraction failed, user input might be read.
 * 
 * @author Łukasz Szafirski
 */
public class ArgumentsService {
    
    /**
     * @param arguments command-line arguments to be processed
     * @return Set of extracted arguments
     * @throws pl.wikihangman.core.arguments.InvalidArgumentsExceptions
     */
    public ApplicationArguments extract(String [] arguments) throws InvalidArgumentsExceptions {
        
        if (arguments.length < 2) {
            throw new InvalidArgumentsExceptions("Invalid number of arguments. Must be 2");
        }
        
        return new ApplicationArguments(arguments[0], arguments[1]);
    }
    
    /**
     * Reads application arguments from given stream
     * 
     * @param input Stream to be read from
     * @return 
     */
    public ApplicationArguments read(InputStream input) {
        
        String user, password;
        
        return new ApplicationArguments(user, password);
    }
    
}
