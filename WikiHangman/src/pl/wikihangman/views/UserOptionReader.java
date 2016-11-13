package pl.wikihangman.views;

import java.util.Scanner;

/**
 * {@code UserOptionReader} is used to get from user one of available options.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UserOptionReader {
    
    private final String[] availableOptions;
    private final String infoMessage;
    
    /**
     * @param availableOptions options from which one will be selected
     * @param infoMessage message that is printed to user before selection
     */
    public UserOptionReader(String[] availableOptions, String infoMessage) {
        
        this.availableOptions = availableOptions;
        this.infoMessage = infoMessage;
    }
    
    /**
     * Prints to the stream all available options.
     * 
     * <p> Options are printed in one line separated by {@code ,} chars.
     */
    public void displayOptions() {
        
        System.out.print('[');
        for (int i = 0; i < availableOptions.length; i++) {
            System.out.print(availableOptions[i]);
            if (i != availableOptions.length - 1) {
                System.out.print(',');
            }
        }
        System.out.println(']');
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
        System.out.print(infoMessage);
        displayOptions();
        Scanner scanner = new Scanner(System.in);
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
