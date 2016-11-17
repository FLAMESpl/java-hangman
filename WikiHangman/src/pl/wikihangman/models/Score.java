package pl.wikihangman.models;

/**
 * {@code Score} represents individual player's score.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Score implements Comparable<Score> {
    
    private String playerName;
    private long points;
    
    public Score setPlayerName(String playerName) {
        return this;
    }
    
    /**
     * 
     * @return player's name
     */
    public String getPlayerName() {
        return playerName;
    }
    
    /**
     * 
     * @return player's amount of points
     */
    public long getPoints() {
        return points;
    }

    @Override
    public int compareTo(Score other) {
        return Long.compare(points, other.points);
    }
}
