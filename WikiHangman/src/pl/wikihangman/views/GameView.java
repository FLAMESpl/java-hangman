package pl.wikihangman.views;

import pl.wikihangman.views.logging.ErrorsEnum;
import java.io.IOException;
import java.util.List;
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
     * Runs input accepting loop for this view.
     * 
     * @param activeUser actually logged in user
     */
    public void start(User activeUser) {
        
        AtomicBoolean exit = new AtomicBoolean(false);
        UserActionReader actionReader = new UserActionReader();
        actionReader.setHeader("Available actions:")
                    .addAction("logout", () -> exit.set(true))
                    .addAction("score", () -> displayScoreBoardView(activeUser))
                    .addAction("start", () -> new HangmanView(this).start(activeUser));
        
        while (!exit.get()) {
            actionReader.read();
        }
    }
    
    private void displayScoreBoardView(User activeUser) {
        
        List<User> users = null;
        try {
            users = getAccountService().getPlayersList();
        } catch(IOException ioException) {
            getLogger().log(ErrorsEnum.DB_IO);
        } catch(NumberFormatException numberFormatException) {
            getLogger().log(ErrorsEnum.DB_FORMAT);
        }
            
        System.out.println(String.format("Your score: %1$d", activeUser.getPoints()));
        for (User user : users) {
            System.out.println(String.format("%1$-20s %2$d", user.getName(), user.getPoints()));
        }
        System.out.println();
    }
}
