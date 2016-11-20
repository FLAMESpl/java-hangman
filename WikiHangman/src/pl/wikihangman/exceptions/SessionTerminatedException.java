package pl.wikihangman.exceptions;

/**
 * Signals that requested session is terminated.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class SessionTerminatedException extends Exception {

    /**
     * Constructs exception with default message.
     */
    public SessionTerminatedException() {
        super("Requested session is terminated");
    }
}
