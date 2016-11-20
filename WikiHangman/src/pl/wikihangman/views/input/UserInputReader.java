package pl.wikihangman.views.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UserInputReader {
    
    private Map<String, Function<String, Object>> questionsMap;
    
    /**
     * Initializes empty hash map for questions.
     */
    public UserInputReader() {
        questionsMap = new HashMap<>();
    }    
    
    /**
     * Adds entry to {@code HashMap} of questions and user inputs converters.
     * 
     * @param question message displayed to the user
     * @param inputConverter converter from input text to desired object type
     * @return this object
     */
    public UserInputReader addQuestion(String question, Function<String, Object> inputConverter) {
        questionsMap.put(question, inputConverter);
        return this;
    }
    
    /**
     * Adds entry to {@code HashMap} of questions without user input conversion
     * defined.
     * 
     * @param question message displayed to the user
     * @return this object
     */
    public UserInputReader addQuestion(String question) {
        questionsMap.put(question, null);
        return this;
    }
    
    /**
     * Prints question to the user then captures his input. Process is repeated
     * as many times as there was entries in {@code HashMap} of questions. 
     * 
     * @return returns formatted list of data provided by user
     */
    public UserInputResult read() {
        
        Scanner scanner = new Scanner(System.in);
        List results = new ArrayList();
        questionsMap.forEach((key, converter) -> {
            System.out.print(key + ": ");
            String input = scanner.next();
            Object newItem = converter == null ? input : converter.apply(input);
            results.add(newItem);
        });
        System.out.println();
        return new UserInputResult(results);
    }
}
