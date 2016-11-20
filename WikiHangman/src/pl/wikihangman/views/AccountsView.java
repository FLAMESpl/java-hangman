package pl.wikihangman.views;

import pl.wikihangman.views.logging.ErrorsEnum;
import pl.wikihangman.views.input.UserActionReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import pl.wikihangman.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.models.User;
import pl.wikihangman.views.input.UserInputReader;
import pl.wikihangman.views.input.UserInputResult;

/**
 * {@code AccountsView} is main application view responsible for creating all
 * other sub-views.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountsView extends ViewBase {
    
    /**
     * Initializes new controllers.
     * 
     * @param constructorDescriptorConsumer {@code FunctionalInterface}
     *      initializing controllers
     */
    public AccountsView(Consumer<ViewBaseConstructorDescriptor> constructorDescriptorConsumer) {
        super(constructorDescriptorConsumer);
    } 
    
    /**
     * Runs input accepting loop for this view.
     * 
     * @param applicationArgs command-line arguments of application
     */
    public void start(String[] applicationArgs) {
        
        final AtomicReference<User> activeUser = new AtomicReference<>();
        activeUser.set(authenticateUsingApplicationArgumentsCredentials(applicationArgs));
        
        AtomicBoolean exit = new AtomicBoolean(false);
        UserActionReader reader = new UserActionReader()
            .setHeader("Available actions:")
            .addAction("exit", () -> exit.set(true))
            .addAction("login", () -> activeUser.set(displayLogInView()))
            .addAction("signup", () -> displaySignUpView());
        
        while (!exit.get()) {
            displayGameViewIfLoggedIn(activeUser);
            reader.read();
        }
    }
    
    /**
     * Runs views associated with game if there is currently logged user.
     * 
     * @param activeUser atomic reference to user
     */
    private void displayGameViewIfLoggedIn(AtomicReference<User> activeUser) {
        if (activeUser.get() != null) {
                GameView gameView = new GameView(this);
                gameView.start(activeUser.get());
                activeUser.set(null);
            }
    }
    
    /**
     * Tries authenticating user using credentials from application's arguments.
     * If it fails, null is returned.
     * 
     * @param applicationArgs user's credentials
     * @return logged in user
     */
    private User authenticateUsingApplicationArgumentsCredentials(String[] applicationArgs) {
        User user = null;
        try {
            user = getAccountService().authenticate(applicationArgs[0], applicationArgs[1]);
            if (user == null) {
                getLogger().log(ErrorsEnum.DB_AUTH, "Failed to use application arguments credentials");
            } else {
                System.out.println("Logged as " + user.getName());
                System.out.println();
            }
        } catch(IOException ioException) {
            getLogger().log(ErrorsEnum.DB_IO);
        } catch(IndexOutOfBoundsException | NumberFormatException formatException) {
            getLogger().log(ErrorsEnum.DB_FORMAT);
        }
        return user;
    }
    
    /**
     * Captures text inputs from user needed to log in to his account
     * and eventually repeats the process if authentication failed.
     * 
     * @return user that logged in using this view
     */
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
                    getLogger().log(ErrorsEnum.DB_AUTH);
                } else {
                    System.out.println("Successfully logged in as " + user.getName());
                    System.out.println();
                }
            } catch(IOException ioException) {
                getLogger().log(ErrorsEnum.DB_IO);
                failure = true;
            } catch(NumberFormatException numberFormatException) {
                getLogger().log(ErrorsEnum.DB_FORMAT);
                failure = true;
            }
            
            if (failure) {
                actionReader.read();
            }
        }
        
        return user;
    }
    
    /**
     * Captures text input from user needed to create new account. Process may
     * be repeated if accesses to database failed.
     */
    private void displaySignUpView() {
        
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
                getAccountService().register(userName, password);
            } catch (IOException ioException) {
                getLogger().log(ErrorsEnum.DB_IO);
                failure = true;
            } catch (EntityAlreadyExistsException entityAlreadyExistsException) {
                getLogger().log(entityAlreadyExistsException);
                failure = true;
            }
            
            if (failure) {
                actionReader.read();
            } else {
                System.out.println("Successfully created account");
                System.out.println();
            }
        }
    }
}
