package pl.wikihangman.exceptions;

/**
 * Signals that given application's arguments set is not valid for application's
 * requirements.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class InvalidArgumentsException extends Exception {
    
    public InvalidArgumentsException() {
        super();
    }
    
    /**
     * 
     * @param message details about this exception.
     */
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
