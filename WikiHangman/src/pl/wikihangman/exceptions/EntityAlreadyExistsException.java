package pl.wikihangman.exceptions;

/**
 * Signals that entity of given unique value already exists in database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EntityAlreadyExistsException extends Exception {
    
    /**
     * 
     * @param field field on which duplication occurred
     * @param value duplicated value
     */
    public EntityAlreadyExistsException(String field, String value) {
        super(String.format("Entity with field `%1$s` of value `%2$s` already exists", field, value));
    } 
}
