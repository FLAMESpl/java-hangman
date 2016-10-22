package pl.wikihangman.exceptions;

/**
 * {@code FileException} Signals that either file stream could not be created
 * or data format is invalid.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class FileException extends Exception{
    
    public FileException() {
        super();
    }
    
    /**
     * 
     * @param message details about this exception.
     * @param cause cause of this exception, null is allowed when there is
     *        no cause.
     */
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
