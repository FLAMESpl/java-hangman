package pl.wikihangman.server.exceptions;

/**
 * Signals exception that occured on server side logic.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public abstract class ServerException extends Exception {

    /**
     * 
     * @param message information about this exception
     */
    public ServerException(String message) {
        super(message);
    }
    
    /**
     * 
     * @param message information abouth this exception
     * @param exception cause of this exception
     */
    public ServerException(String message, Exception exception) {
        super(message, exception);
    }
}
