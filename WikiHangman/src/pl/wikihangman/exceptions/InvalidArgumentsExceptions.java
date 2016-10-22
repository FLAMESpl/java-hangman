package pl.wikihangman.exceptions;

/**
 * Signals that given application's arguments set is not valid for application's
 * requirements.
 * 
 * @author Łukasz Szafirski
 */
public class InvalidArgumentsExceptions extends Exception {
    
    public InvalidArgumentsExceptions() {
        super();
    }
    
    /**
     * 
     * @param message details about this exception.
     */
    public InvalidArgumentsExceptions(String message) {
        super(message);
    }
}
