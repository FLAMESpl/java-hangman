package pl.wikihangman.server.exceptions;

/**
 * Signals that entity of given unique value already exists in database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EntityAlreadyExistsException extends ServerException {
    
    private final String field;
    
    /**
     * 
     * @param field field on which duplication occurred, displayed in title 
     *      of notification
     * @param value duplicated value
     */
    public EntityAlreadyExistsException(String field, String value) {
        super(String.format("Entity with field `%1$s` of value `%2$s` already exists", field, value));
        this.field = field;
    }
    
    /**
     * @return title of this notification
     */
    public String getTitle() {
        return field + "must be unique";
    }
}
