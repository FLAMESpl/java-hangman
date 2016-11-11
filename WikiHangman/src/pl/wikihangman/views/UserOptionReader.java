package pl.wikihangman.views;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Function;

/**
 * {@code UserOptionReader} is used to get from user one of available options.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UserOptionReader {
    
    private final PrintStream outputStream;
    private final InputStream inputStream;
    private final String[] availableOptions;
    private final String infoMessage;
    
    /**
     * @param outputStream stream to be write to
     * @param inputStream stream to be read from
     * @param availableOptions options from which one will be selected
     * @param infoMessage message that is printed to user before selection
     */
    public UserOptionReader(PrintStream outputStream, InputStream inputStream, 
            String[] availableOptions, String infoMessage) {
        
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.availableOptions = availableOptions;
        this.infoMessage = infoMessage;
    }
    
    /**
     * Prints to the stream all available options.
     * 
     * <p> Options are printed in one line separated by {@code ,} chars.
     */
    public void displayOptions() {
        
        outputStream.print('[');
        for (int i = 0; i < availableOptions.length; i++) {
            outputStream.print(availableOptions[i]);
            if (i != availableOptions.length - 1) {
                outputStream.print(',');
            }
        }
        outputStream.println(']');
    }
    
    /**
     * Displays information to the user including available options, then
     * returns index of chosen option.
     * 
     * <p> If no option is valid negative number is returned.
     * 
     * @return index of chosen option
     */
    public int read() {
        outputStream.print(infoMessage);
        displayOptions();
        Scanner scanner = new Scanner(inputStream);
        String readValue = scanner.next();
        
        int i = availableOptions.length - 1;
        while (i >= 0) {
            if (readValue.equals(availableOptions[i])) {
                break;
            }
            i--;
        }
        
        return i;
    }
}
