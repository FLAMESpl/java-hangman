package pl.wikihangman.server.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.*;
import java.util.Date;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ServerLogger {

    private final PrintStream stream;
    private Path outputFile = null;
    private boolean fileLogging = false;
    
    /**
     * 
     * @param stream stream where all messages are printed to
     */
    public ServerLogger(PrintStream stream) {
        this.stream = stream;
    }
    
    /**
     * Enables logging to file specified by path name.
     * 
     * @param filePath path to the output file
     * @return this object
     */
    public ServerLogger outputToFile(String filePath) {
        outputFile = Paths.get(filePath);
        fileLogging = true;
        return this;
    }
    
    /**
     * Prints message to previously set stream and optional file.
     * 
     * @param message message to be printed
     */
    public void log(String message) {
        Date date = new Date();
        message = "[" + date.toString() + "] " + message;
        stream.println(message);
        if (fileLogging) {
            logToFile(message);
        }
    }
    
    /**
     * Logs gives message to the file
     * 
     * @param message message to log
     */
    private void logToFile(String message) {
        File file = outputFile.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            Files.write(outputFile, (message + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ioException) {
            stream.println("File logging exception occured : disabling.");
            fileLogging = false;
        }
    }
}
