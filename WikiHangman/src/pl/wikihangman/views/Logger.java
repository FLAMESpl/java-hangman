package pl.wikihangman.views;

/**
 * {@code Logger} allows displaying error messages to the user based on a given
 * code name.
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
}
