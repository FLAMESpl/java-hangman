package pl.wikihangman.views;

/**
 * {@code Logger} allows displaying error messages based on code names and types.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Logger {
    
    /**
     * Prints to error stream message from given {@code ErrorsEnum} with
     * additional message in new line.
     * 
     * @param errorEnum code name of error
     * @param additionalMessage additional message printed after standard message
     * @see ErrorsEnum
     */
    public void log(ErrorsEnum errorEnum, String additionalMessage) {
        System.err.println(errorEnum.getMessage());
        if (additionalMessage != null) {
            System.err.println(additionalMessage);
        }
    }
    
    /**
     * Prints to error stream message from given {@code ErrorsEnum}.
     * 
     * @param errorEnum code name of error
     * @see ErrorsEnum
     */
    public void log(ErrorsEnum errorEnum) {
        log(errorEnum, null);
    }
    
    /**
     * Prints exception message to error stream with additional message in
     * new line.
     * 
     * @param exception exception of which message is printed
     * @param additionalMessage additional message printed after standard message
     */
    public void log(Exception exception, String additionalMessage) {
        System.err.println(exception.getMessage());
        if (additionalMessage != null) {
            System.err.println(additionalMessage);
        }
    }
    
    /**
     * Prints exception message to error stream.
     * 
     * @param exception exception of which message is printed
     */
    public void log(Exception exception) {
       log(exception, null); 
    }
}
