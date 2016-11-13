package pl.wikihangman.infrastructure;

import java.io.ByteArrayInputStream;

/**
 * Provides support for simulating user input in tests.
 * 
 * @author Łukasz Szafirski
 */
public class StreamHelper {
   
    /**
     * Prepares set of words that simulates next user inputs.
     * 
     * @param tokens tokens serialized into input stream
     */
    public static void prepareSequence(String... tokens) {
        String preparedString = String.join("\n", tokens);
        ByteArrayInputStream stream = new ByteArrayInputStream(preparedString.getBytes());
        System.setIn(stream);
    }
}
