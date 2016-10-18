package pl.wikihangman.core.arguments;

/**
 * Set of application arguments extracted from command-line
 * 
 * @author  Łukasz Szafirski
 */
public class ApplicationArguments {
    
    private final String user;
    private final String password;
    
    /**
     * @param arguments command-line arguments to be processed
     * @throws pl.wikihangman.core.arguments.InvalidArgumentsExceptions
     */
    public ApplicationArguments(String[] arguments) throws InvalidArgumentsExceptions {
        
        if (arguments.length < 2) {
            throw new InvalidArgumentsExceptions("Invalid number of arguments. Must be 2");
        }
        
        user = arguments[0];
        password = arguments[1];
    }
    
    /**
     * @return Requested user's login
     */
    public final String getUser() {
        return user;
    }
    
    /**
     * @return Requested user's password
     */
    public final String getPassword() {
        return password;
    }
}
