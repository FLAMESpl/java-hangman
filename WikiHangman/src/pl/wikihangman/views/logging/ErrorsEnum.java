package pl.wikihangman.views.logging;

/**
 * {@code Enum} containing possible error messages.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum ErrorsEnum implements INotification {
    
    APP_ARGS("Could not parse application arguments to ip address and port", "Startup Error"),
    COMMUNICATION("Error has occured while sending / receiving data", "Network Error"),
    DB_IO("Could not read/write to database file.", "IO Error"),
    DB_FORMAT("Could not read from database, data is corrupted.", "Database Data Error"),
    DB_AUTH("Invalid credentials.", "Loggin In Error"),
    INPUT_NEED_SINGLE("Only single character is valid.", "Input Error"),
    INPUT_EMPTY("Input value cannot be empty.", "Input Error"),
    NEED_AUTH("Log in first to perform requested action.", "Authorization failure"),
    SOCKET_INIT("Could not establish communication with server.", "Tcp Socket Failure");
    
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
