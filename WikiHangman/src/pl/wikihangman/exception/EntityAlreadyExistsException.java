package pl.wikihangman.exception;

/**
 * Signals that entity of given unique value already exists in database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EntityAlreadyExistsException extends Exception {
    
    /**
     * 
     * @param message information about error
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    } 
}
