package pl.wikihangman.views;

import pl.wikihangman.views.input.UserActionReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import pl.wikihangman.models.User;
import pl.wikihangman.views.input.UserInputReader;
import pl.wikihangman.views.input.UserInputResult;

/**
 * {@code MasterView} is main application view responsible for creating all
 * other sub-views.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class MasterView extends ViewBase {
    
    private Logger logger = new Logger();
    private User activeUser = null;
    private AccountsView accountsView;
    private GameView gameView;
    
    /**
     * Initializes new controllers.
     * 
     * @param constructorDescriptorConsumer {@code FunctionalInterface}
     *      initializing controllers
     */
    public MasterView(Consumer<ViewBaseConstructorDescriptor> constructorDescriptorConsumer) {
        super(constructorDescriptorConsumer);
        accountsView = new AccountsView(this);
        gameView = new GameView(this);
    } 
    
    /**
     * Entry point for application, loops for reading user input.
     * 
     * @param applicationArgs command-line arguments of application
     */
    public void start(String[] applicationArgs) {
        
        try {
            activeUser = getAccountService().authenticate(applicationArgs[0], applicationArgs[1]);
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
        UserActionReader reader = accountViewsReader(exit);
        
        while (!exit.get()) {
            if (activeUser != null) {
                gameView.display(activeUser);
                activeUser = null;
            }
            reader.read();
        }
    }
    
    private UserActionReader accountViewsReader(AtomicBoolean exitToken) {
        return new UserActionReader().setHeader("Available actions:")
              .addAction("exit", () -> exitToken.set(true))
              .addAction("login", () -> activeUser = displayLogInView())
              .addAction("signup", () -> accountsView.displaySignUpView());
    }
    
    private User displayLogInView() {
        
        User user = null;
        AtomicBoolean retry = new AtomicBoolean(true);
        UserActionReader actionReader = new UserActionReader();
        actionReader.setHeader("Try again?")
                    .addAction("yes", () -> retry.set(true))
                    .addAction("no", () -> retry.set(false));
        
        UserInputReader inputReader = new UserInputReader();
        inputReader.addQuestion("User name")
                   .addQuestion("Password");
        
        while(retry.get()) {
            boolean failure = false;
            retry.set(false);
            
            UserInputResult inputResult = inputReader.read();
            String userName = inputResult.get(0);
            String password = inputResult.get(1);
        
            try {
                user = getAccountService().authenticate(userName, password);
                if (user == null) {
                    failure = true;
                    logger.log(ErrorsEnum.DB_AUTH);
                } else {
                    System.out.println("Successfully logged in as " + user.getName());
                    System.out.println();
                }
            } catch(IOException ioException) {
                logger.log(ErrorsEnum.DB_IO);
                failure = true;
            } catch(NumberFormatException numberFormatException) {
                logger.log(ErrorsEnum.DB_FORMAT);
                failure = true;
            }
            
            if (failure) {
                actionReader.read();
            }
        }
        
        return user;
    }
}
