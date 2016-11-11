package pl.wikihangman.views;

import java.util.Scanner;
import java.util.function.Function;

/**
 * {@code UserInputReader} is responsible for reading variable from input stream
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class UserInputReader {
    
    /**
     * Returns complete string token read from user input.
     * 
     * @param question information printed to user before he starts typing
     * @return string read from user input
     */
    public String read(String question) {
        
        System.out.print(question);
        Scanner reader = new Scanner(System.in);
        return reader.next();
    }
    
    /**
     * Returns array of strings read from user input.
     * 
     * @param questions source of informations printed in to user before each input
     * @return array of strings read from user input
     */
    public String[] read(String[] questions) {
        
        int arrayLength = questions.length;
        String[] results = new String[arrayLength];
        Scanner reader = new Scanner(System.in);
        for (int i = 0; i < arrayLength; i++) {
            System.out.print(questions[i]);
            results[i] = reader.next();
        }
        return results;
    }
    
    /**
     * Returns variable converter from complete string token read from user input.
     * 
     * @param <T> any type constructed by rules specified in converter
     * @param question information printed to user before he starts typing
     * @param converter function converting string token to return type
     * @return object converted from user input
     */
    public <T> T read(String question, Function<String, T> converter) {
        
        String result;
        System.out.print(question);
        Scanner reader = new Scanner(System.in);
        result = reader.next();
        return converter.apply(result);
    }
    
    /**
     * Returns variable converter from array of complete string tokens 
     * read from user input.
     * 
     * @param <T> any type constructed by rules specified in converter
     * @param questions source of informations printed in to user before each input
     * @param converter function converting string tokens to return type
     * @return array of objects converted from user input
     */
    public <T> T read(String[] questions, Function<String[], T> converter) {
        
        int arraySize = questions.length;
        String[] results = new String[arraySize];
        Scanner reader = new Scanner(System.in);
        for (int i = 0; i < arraySize; i++) {
            System.out.print(questions[i]);
            results[i] = reader.next();
        }
        return converter.apply(results);
    }
}