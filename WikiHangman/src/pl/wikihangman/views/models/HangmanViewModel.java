package pl.wikihangman.views.models;

import java.util.ArrayList;
import java.util.List;
import pl.wikihangman.models.Letter;

/**
 * View model of {@code Hangman} domain model, which does not expose full
 * keyword.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class HangmanViewModel {

    private int maxLives;
    private int actualLives;
    private List<Letter> keyword;
    
    /**
     * 
     * @param maxLives initial amount of available lives in this riddle
     * @return this object
     */
    public HangmanViewModel setMaxLives(int maxLives) {
        this.maxLives = maxLives;
        return this;
    }
    
    /**
     * 
     * @param actualLives amout of remaining lives in this riddle
     * @return this object
     */
    public HangmanViewModel setActualLives(int actualLives) {
        this.actualLives = actualLives;
        return this;
    }
    
    /**
     * Creates secret keyword from full one.
     * 
     * @param fullKeyword keyword including undiscovered characters
     * @return this object
     * @throws IndexOutOfBoundsException 
     */
    public HangmanViewModel createKeyword(List<Letter> fullKeyword) {
        keyword = new ArrayList<>();
        for(Letter letter : fullKeyword) {
            Letter newLetter = new Letter();
            if (letter.isDiscovered()) {
                newLetter.setDiscovered(letter.getCharacter());
            } else {
                newLetter.setUndiscovered();
            }
            keyword.add(newLetter);
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
