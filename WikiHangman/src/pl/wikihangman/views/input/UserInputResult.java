package pl.wikihangman.views.input;

import java.util.List;

/**
 * Results of user input returned from {@code UserInputReader}.
 * Wraps logic of casting items of {@code List<Object>} to certain types.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 * @see UserInputReader
 */
public class UserInputResult {
    
    private final List<Object> results;
    
    /**
     * 
     * @param results list of converted objects from user text input
     */
    public UserInputResult(List<Object> results) {
        this.results = results;
    }
    
    /**
     * Gets from list object of given index and casts it to desired type.
     * Operation is type unsafe.
     * 
     * @param <T> type casted to
     * @param index index of object in list
     * @return casted object to type {@code T}
     */
    public <T> T get(int index) {
        return (T)results.get(index);
    }
}
