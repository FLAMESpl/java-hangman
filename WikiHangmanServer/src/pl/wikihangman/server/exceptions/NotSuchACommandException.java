package pl.wikihangman.server.exceptions;

/**
 * Signals there is no recognizable command registered in the system.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class NotSuchACommandException extends ServerException {

    /**
     * 
     * @param commandName name of non-existing command
     */
    public NotSuchACommandException(String commandName) {
        super(commandName + " is not recognizable command.");
    }
}
