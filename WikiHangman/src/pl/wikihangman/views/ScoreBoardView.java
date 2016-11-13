package pl.wikihangman.views;

import java.util.List;
import pl.wikihangman.models.Score;
import pl.wikihangman.models.ScoreBoard;

/**
 *
 * @author Łukasz Szafirski
 */
public class ScoreBoardView {
    
    private final ScoreBoard scoreBoard;
    
    public ScoreBoardView(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
    
    public void display() {
        
        List<Score> scores = scoreBoard.getAllScore();
        
        System.out.println("Your score: " + scoreBoard.getActiveUserScore());
        System.out.println();
        
        for(Score score : scores) {
            System.out.println(String.format("%1$-30s%2$d", score.getPlayerName(), score.getPoints()));
        }
    }
}
