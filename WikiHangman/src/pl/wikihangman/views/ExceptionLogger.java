package pl.wikihangman.views;

import java.io.PrintStream;

/**
 * Used to print messages from exceptions.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ExceptionLogger {
    
    private final PrintStream stream;
    
    /**
     * @param stream stream where exception message will be printed
     */
    public ExceptionLogger(PrintStream stream) {
        this.stream = stream;
    }
    
    /**
     * Prints exception's message to output stream
     * 
     * @param exception exception of which message will be printed
     */
    public void log(Exception exception) {
        stream.println(exception.getMessage());
    }
    
    /**
     * Prints custom message when exception's one is not sufficient
     * 
     * @param exceptionMessage message to be printed
     */
    public void log(String exceptionMessage) {
        stream.println(exceptionMessage);
    }
}
