package pl.wikihangman.views;

import java.io.PrintStream;

/**
 * Used to print messages from exceptions.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ExceptionLogger {
    
    private final Exception exception;
    
    /**
     * @param exception exception from which message is logged to the stream
     */
    public ExceptionLogger(Exception exception) {
        this.exception = exception;
    }
    
    /**
     * Prints exception's message to output stream
     * 
     * @param stream target print stream
     */
    public void log(PrintStream stream) {
        stream.println(exception.getMessage());
    }
}
