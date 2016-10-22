package pl.wikihangman.views;

import java.io.PrintStream;

/**
 * Used to print messages from exceptions.
 * 
 * @author Łukasz Szafirski
 */
public class ExceptionLogger {
    
    private final Exception exception;
    
    /**
     * Exception from which message is logged to the stream.
     * 
     * @param exception 
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
