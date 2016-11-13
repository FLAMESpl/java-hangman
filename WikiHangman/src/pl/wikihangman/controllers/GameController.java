package pl.wikihangman.controllers;

import java.util.Collections;
import java.util.List;
import pl.wikihangman.exceptions.FileException;
import pl.wikihangman.models.Score;
import pl.wikihangman.models.ScoreBoard;
import pl.wikihangman.models.User;
import pl.wikihangman.services.ScoreService;
import pl.wikihangman.views.ExceptionLogger;
import pl.wikihangman.views.ScoreBoardView;

/**
 *
 * @author Łukasz Szafirski
 */
public class GameController {
    
    private User user;
    private final ExceptionLogger logger;
    
    /**
     * 
     * @param user currently logged user 
     */
    public GameController(User user) {
        this.user = user;
        this.logger = new ExceptionLogger(System.err);
    }
    
    /**
     * Displays score of all users from database
     * 
     * @param dbPath path to the users database
     */
    public void requestScore(String dbPath) {
        
        ScoreService scoreService = new ScoreService();
        try {
            List<Score> scores = scoreService.scoresFromFile(dbPath);
            scores.sort(Collections.reverseOrder((x, y) -> x.compareTo(y)));
            ScoreBoard scoreBoard = new ScoreBoard(user.getPoints(), scores);
            ScoreBoardView scoreBoardView = new ScoreBoardView(scoreBoard);
            scoreBoardView.display();
            
        } catch (FileException fileException) {
            logger.log(fileException);
        }
    }
}
