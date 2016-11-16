package pl.wikihangman.views;

/**
 * {@code Enum} containing possible error messages.
 * 
 * @author Łukasz Szafirski
 */
public enum ErrorsEnum {
    
    DB_IO("Could not read/write to database file."),
    DB_FORMAT("Could not read from database, data is corrupted.");
    
    private final String message;
    
    /**
     * 
     * @param message error message
     */
    private ErrorsEnum(String message) {
        this.message = message;
    }
    
    /**
     * 
     * @return error message
     */
    public String getMessage() {
        return message;
    }
}
