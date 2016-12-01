package pl.wikihangman.views.logging;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum ConfirmationsEnum implements INotification {

    USER_CREATED("User has been successfully created", "Success");
    
    private final String message;
    private final String title;
    
    /**
     * 
     * 
     * @param message informative message about this notification
     * @param title 
     */
    private ConfirmationsEnum(String message, String title) {
        this.message = message;
        this.title = title;
    }
    
    /**
     * 
     * @return title of this notification
     */
    @Override
    public String getTitle() {
        return title;
    }
    
    /**
     * @return message of this notification
     */
    @Override
    public String getMessage() {
        return message;
    }
}
