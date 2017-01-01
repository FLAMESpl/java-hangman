package pl.wikihangman.views.logging;

/**
 * Notification with default messages.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class DefaultSuccessNotification implements INotification {

    /**
     * Produces "Success" string.
     * 
     * @return title of this notification
     */
    @Override
    public String getTitle() {
        return "Success";
    }

    /**
     * Produces "Requested operation was executed successfully" string.
     * 
     * @return body message of this notification
     */
    @Override
    public String getMessage() {
        return "Requested operation was executed successfully";
    }

    
}
