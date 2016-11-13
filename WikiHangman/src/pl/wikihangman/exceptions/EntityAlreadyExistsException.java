package pl.wikihangman.exceptions;

/**
 * Signals that entity of given unique fields already exists in database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EntityAlreadyExistsException extends Exception {
    
    public EntityAlreadyExistsException() {
        super();
    }
    
    /**
     * 
     * @param message details about this exception.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
