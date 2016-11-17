package pl.wikihangman.views;

import java.util.concurrent.atomic.AtomicBoolean;
import pl.wikihangman.models.User;
import pl.wikihangman.views.input.UserActionReader;

/**
 * {@code GameView} is responsible for application's view after activeUser logged in.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GameView extends ViewBase {
    
    /**
     * Copies all services from its parent.
     * 
     * @param parent calling view
     */
    public GameView(ViewBase parent) {
        super(parent);
    }
    
    /**
     * Runs loop for this view.
     * @param activeUser actually logged in user
     */
    public void display(User activeUser) {
        
        AtomicBoolean exit = new AtomicBoolean(false);
        UserActionReader actionReader = new UserActionReader();
        actionReader.setHeader("Available actions:")
                    .addAction("logout", () -> exit.set(true))
                    .addAction("score", () -> getAccountService().getScoreBoard());
    }
    
    private void displayScoreBoard()
}
