package pl.wikihangman.views;

import pl.wikihangman.views.input.UserActionReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import pl.wikihangman.controllers.AccountController;
import pl.wikihangman.models.User;

/**
 * {@code MasterView} is main application view responsible for creating all
 * other sub-views.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class MasterView {
    
    private AccountController accountController = new AccountController();;
    private AccountsView accountsView = new AccountsView();
    private GameView gameView = new GameView();
    private Logger logger = new Logger();
    private User activeUser = null;
    
    /**
     * Entry point for application, loops for reading user input.
     * 
     * @param applicationArgs command-line arguments of application
     */
    public void start(String[] applicationArgs) {
        
        try {
            activeUser = accountController.authenticate(applicationArgs[0], applicationArgs[1]);
            if (activeUser == null) {
                logger.log(ErrorsEnum.DB_AUTH, "Failed to use application arguments credentials");
            } else {
                System.out.println("Logged as " + activeUser.getName());
                System.out.println();
            }
        } catch(IOException ioException) {
            logger.log(ErrorsEnum.DB_IO);
        } catch(IndexOutOfBoundsException | NumberFormatException formatException) {
            logger.log(ErrorsEnum.DB_FORMAT);
        }
        
        AtomicBoolean exit = new AtomicBoolean(false);
        UserActionReader reader = new UserActionReader();
        reader.setHeader("Available actions:")
              .addAction("exit", () -> exit.set(true))
              .addAction("login", () -> activeUser = accountsView.displayLogInView())
              .addAction("signup", () -> accountsView.displaySignUpView());
        
        while (!exit.get()) {
            if (activeUser != null) {
                gameView.display(activeUser);
            }
            reader.read();
        }
    }
}
