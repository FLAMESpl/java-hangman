package pl.wikihangman.views.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * {@code UserActionReader} allows reading one of allowed inputs from user
 * and taking associated action.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0 
 */
public class UserActionReader {
    
    private Map<String, Runnable> availableActions;
    private String header;
    
    /**
     * Initializes empty map of available actions.
     */
    public UserActionReader() {
        availableActions = new HashMap<>();
    }
    
    /**
     * Adds new entry to available actions map. Null actions are acceptable for
     * options that do nothing.
     * 
     * @param actionName name of the action
     * @param action action that is taken when user types its name
     * @return this object
     */
    public UserActionReader addAction(String actionName, Runnable action) {
        availableActions.put(actionName, action);
        return this;
    }
    
    /**
     * 
     * @param header header displayed before listing available actions
     * @return this object
     */
    public UserActionReader setHeader(String header) {
        this.header = header;
        return this;
    }
    
    /**
     * Prints header, available actions and then awaits for user input. 
     * Process is repeated until response matches one of given options.
     */
    public void read() {
        
        String userInput;
        String question = header + " " + String.join(", ", availableActions.keySet());
        Scanner scanner = new Scanner(System.in);
        
        do {
            System.out.println(question);
            userInput = scanner.next();
            System.out.println();
            
        } while(!availableActions.containsKey(userInput));
        
        Runnable action = availableActions.get(userInput);
        if (action != null) {
            action.run();
        }
    }
}
