package pl.wikihangman.server.logging;

import java.io.PrintStream;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ServerLogger {

    private final PrintStream stream;
    
    /**
     * 
     * @param stream stream where all messages are printed to
     */
    public ServerLogger(PrintStream stream) {
        this.stream = stream;
    }
    
    /**
     * Prints message to previously set stream.
     * 
     * @param message message to be printed
     */
    public void log(String message) {
        stream.print(message);
    }
}
