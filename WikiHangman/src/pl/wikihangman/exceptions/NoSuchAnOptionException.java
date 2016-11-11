package pl.wikihangman.exceptions;

/**
 * Signals unexpected user input
 * 
 * @author Łukasz Szafirski
 */
public class NoSuchAnOptionException extends Exception{
    
     public NoSuchAnOptionException() {
        super();
    }
    
    /**
     * 
     * @param message details about this exception.
     */
    public NoSuchAnOptionException(String message) {
        super(message);
    }
}
