package pl.wikihangman.exceptions;

import pl.wikihangman.views.logging.INotification;

/**
 * Signals that requested session is terminated.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class SessionTerminatedException extends Exception implements INotification {

    /**
     * Constructs exception with default message.
     */
    public SessionTerminatedException() {
        super("Requested session is terminated");
    }
    
    /**
     * @return title of this notification
     */
    @Override
    public String getTitle() {
        return "Session error";
    }
}
