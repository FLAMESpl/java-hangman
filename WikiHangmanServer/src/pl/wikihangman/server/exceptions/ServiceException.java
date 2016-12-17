package pl.wikihangman.server.exceptions;

/**
 * Signals that some service has thrown exception.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ServiceException extends ServerException {

    /**
     * 
     * @param exception cause of this exception
     */
    public ServiceException(Exception exception) {
        super(exception.getMessage(), exception);
    }
    
    
}
