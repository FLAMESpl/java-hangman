package pl.wikihangman.views.logging;

/**
 * Interface for messages and their titles represented as code values.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public interface INotification {

    /**
     * 
     * @return general title about this notification
     */
    public String getTitle();
    
    /**
     * 
     * @return informative message about this notification
     */
    public String getMessage();
}
