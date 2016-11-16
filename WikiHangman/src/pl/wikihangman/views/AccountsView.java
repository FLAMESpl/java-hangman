package pl.wikihangman.views;

import pl.wikihangman.views.input.UserInputReader;
import pl.wikihangman.views.input.UserActionReader;
import pl.wikihangman.views.input.UserInputResult;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import pl.wikihangman.controllers.AccountController;
import pl.wikihangman.exception.EntityAlreadyExistsException;
import pl.wikihangman.models.User;

/**
 * {@code AccountsView} is used to allow user to manipulate user database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountsView {
    
    Logger logger = new Logger();
    
    /**
     * Captures text inputs from user needed to log in to his account
     * and eventually repeats the process if authentication failed.
     * 
     * @return user that logged in using this view
     */
    public User displayLogInView() {
        
        User user = null;
        AtomicBoolean retry = new AtomicBoolean(true);
        UserActionReader actionReader = new UserActionReader();
        actionReader.setHeader("Try again?")
                    .addAction("yes", () -> retry.set(true))
                    .addAction("no", () -> retry.set(false));
        
        AccountController accountController = new AccountController();
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
                user = accountController.authenticate(userName, password);
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
    
    /**
     * Captures text input from user needed to create new account. Process may
     * be repeated if accesses to database failed.
     */
    public void displaySignUpView() {
        
        AtomicBoolean retry = new AtomicBoolean(true);
        UserActionReader actionReader = new UserActionReader();
        actionReader.setHeader("Try again?")
                    .addAction("yes", () -> retry.set(true))
                    .addAction("no", () -> retry.set(false));
        
        AccountController accountController = new AccountController();
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
                accountController.register(userName, password);
            } catch (IOException ioException) {
                logger.log(ErrorsEnum.DB_IO);
                failure = true;
            } catch (EntityAlreadyExistsException entityAlreadyExistsException) {
                logger.log(entityAlreadyExistsException);
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
