package pl.wikihangman.views.logging;

/**
 * {@code Enum} containing possible error messages.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum ErrorsEnum implements INotification {
    
    DB_IO("Could not read/write to database file.", "IO Error"),
    DB_FORMAT("Could not read from database, data is corrupted.", "Database Data Error"),
    DB_AUTH("Invalid credentials.", "Loggin In Error"),
    INPUT("Invalid form of an input.", "Input Error");
    
    private final String message;
    private final String title;
    
    /**
     * 
     * @param message error message
     * @param title title of message
     */
    private ErrorsEnum(String message, String title) {
        this.message = message;
        this.title = title;
    }
    
    /**
     * 
     * @return title of error message
     */
    @Override
    public String getTitle() {
        return title;
    }
    
    /**
     * 
     * @return error message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
