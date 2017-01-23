package pl.wikihangman.web.models;

/**
 * Represents state of single letter in keyword
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Letter {
    
    private boolean discovered;
    private char character;
    
    /**
     * 
     * @param character character to be set
     * @return this object
     */
    public Letter setCharacter(char character) {
        this.character = character;
        return this;
    }
    
    /**
     * 
     * @param discovered status of letter's visibility
     * @return 
     */
    public Letter setIfIsDiscovered(boolean discovered) {
        this.discovered = discovered;
        return this;
    }
    
    /**
     * 
     * @return true if {@code Letter} status is discovered, otherwise false
     */
    public boolean isDiscovered() {
        return discovered;
    }
    
    /**
     * Returns {@code Letter} character if status is discovered, otherwise 
     * it returns null character.
     * 
     * @return {@code Letter} character.
     */
    public char getCharacter() {
        return character;
    }
}
