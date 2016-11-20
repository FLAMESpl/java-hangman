package pl.wikihangman.services;

/**
 * Represents game session of single player saved in-memory on the server.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GameSession {

    private int userId;
    private String keywordsText;
    
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
     * @param keywordsText text of the keyword user is solving
     * @return 
     */
    public GameSession setKeywordsText(String keywordsText) {
        this.keywordsText = keywordsText;
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
     * @return text of the keyword user is solving
     */
    public String getKeywordsText() {
        return keywordsText;
    }
}
