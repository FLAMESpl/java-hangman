package pl.wikihangman.models;

import java.util.ArrayList;
import java.util.List;

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
     * @return 
     */
    public Hangman setMaxLives(int maxLives) {
        this.maxLives = maxLives;
        return this;
    }
    
    /**
     * 
     * @param actualLives amout of remaining lives in this riddle
     * @return 
     */
    public Hangman setActualLies(int actualLives) {
        this.actualLives = actualLives;
        return this;
    }
    
    /**
     * Creates keyword with discovered letters at given indices.
     * 
     * @param text text of the keyword
     * @param discoveredLettersIndices indices of already discovered letters in
     *      keyword
     * @return this object
     * @throws IndexOutOfBoundsException 
     */
    public Hangman createKeyword(String text, int... discoveredLettersIndices) 
        throws IndexOutOfBoundsException {
        
        keyword = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            keyword.add(new Letter().setUndiscovered());
        }
        for (int index : discoveredLettersIndices) {
            keyword.get(index).setDiscovered(text.charAt(index));
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
}
