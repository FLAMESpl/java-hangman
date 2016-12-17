package pl.wikihangman.server.exceptions;

/**
 * Signals that given command name is correct but its options don't match.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class CommandOptionsException extends ServerException {

    /**
     * 
     * @param message information about this exception
     */
    public CommandOptionsException(String message) {
        super(message);
    }
}
