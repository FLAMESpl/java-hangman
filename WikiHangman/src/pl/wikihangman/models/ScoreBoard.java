package pl.wikihangman.models;

import java.util.List;

/**
 * Represents score board of all users in database with active user's score on
 * top of it.
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ScoreBoard {
    
    private long activeUserScore;
    private List<Score> allScore;
    
    /**
     * 
     * @param activeUserScore logged user's score
     * @param allScore score of all users in database including logged user
     */
    public ScoreBoard(long activeUserScore, List<Score> allScore) {
        this.activeUserScore = activeUserScore;
        this.allScore = allScore;
    }
    
    /**
     * 
     * @return logged user's score
     */
    public long getActiveUserScore() {
        return activeUserScore;
    }
    
    /**
     * 
     * @return score of all users in database including logged user
     */
    public List<Score> getAllScore() {
        return allScore;
    }
}
