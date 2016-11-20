package pl.wikihangman.services;

import pl.wikihangman.models.Hangman;

/**
 * Represents game session of single player saved in-memory on the server.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GameSession {

    private int userId;
    private Hangman hangman;
    
    /**
     * 
     * @param userId id of user associated with this session
     * @return 
     */
    public GameSession setUserId(int userId) {
        this.userId = userId;
        return this;
    }
    
    /**
     * 
     * @param hangman hangman user is solving
     * @return 
     */
    public GameSession setHangman (Hangman hangman) {
        this.hangman = hangman;
        return this;
    }
    
    /**
     * 
     * @return id of user associated with this session
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * 
     * @return hangman user is solving
     */
    public Hangman getHangman() {
        return hangman;
    }
}
