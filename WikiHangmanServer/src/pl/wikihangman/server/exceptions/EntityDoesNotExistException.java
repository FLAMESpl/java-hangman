package pl.wikihangman.server.exceptions;

/**
 * Signals that entity of given key does not exists in collection.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EntityDoesNotExistException extends ServerException {

    /**
     * 
     * @param field field whiches value has not been found
     * @param value value not found
     */
    public EntityDoesNotExistException(String field, String value) {
        super(String.format("Entity with field `%1$s` of value `%2$s` does not exists", field, value));
    }
}
