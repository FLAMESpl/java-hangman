package pl.wikihangman.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents actual game state including lost lives, discovered letters
 * and keyword's length
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Hangman {
    
    private int maxLives;
    private int actualLives;
    private List<Letter> keyword;
    
    /**
     * 
     * @param maxLives initial amount of available lives in this riddle
     * @return this object
     */
    public Hangman setMaxLives(int maxLives) {
        this.maxLives = maxLives;
        return this;
    }
    
    /**
     * 
     * @param actualLives amout of remaining lives in this riddle
     * @return this object
     */
    public Hangman setActualLives(int actualLives) {
        this.actualLives = actualLives;
        return this;
    }
    
    /**
     * Creates fully undiscovered keyword from given string, but with known
     * characters.
     * 
     * @param text text of the keyword
     * @return this object
     * @throws IndexOutOfBoundsException 
     */
    public Hangman createKeyword(String text) 
        throws IndexOutOfBoundsException {
        
        keyword = new ArrayList<>();
        for (char character : text.toCharArray()) {
            keyword.add(new Letter().setIfIsDiscovered(false).setCharacter(character));
        }
        return this;
    }
    
    /**
     * 
     * @return amount of letters in keyword
     */
    public int getKeywordsLength() {
        return keyword.size();
    }
    
    /**
     * 
     * @return initial amout of available lives in this riddle
     */
    public int getMaxLives() {
        return maxLives;
    }
    
    /**
     * 
     * @return actual amount of available lives in this riddle
     */
    public int getActualLives() {
        return actualLives;
    }
    
    /**
     * 
     * @return current state of keyword in this riddle
     */
    public List<Letter> getKeyword() {
        return keyword;
    }
    
    /**
     * Discovers all letters of given character in keyword, if there is none,
     * one live is lost.
     * 
     * @param character character to discover in keyword
     * @return total letters discovered
     */
    public int discover(char character) {
        AtomicInteger discovered = new AtomicInteger(0);
        char lowerChar = Character.toLowerCase(character);
        keyword.stream()
                .filter((x) -> Character.toLowerCase(x.getCharacter()) == lowerChar)
                .forEach((x) -> {
                    x.setIfIsDiscovered(true);
                    discovered.incrementAndGet();
                });
        if (discovered.get() == 0) {
            actualLives--;
        }
        return discovered.get();
    }
    
    /**
     * Tests if keyword has any undiscovered letters remaining.
     * 
     * @return true if keyword has any undiscovred letters, otherwise false
     */
    public boolean hasUndiscoveredLetters() {
        return keyword.stream().anyMatch(x -> !x.isDiscovered());
    }
}
