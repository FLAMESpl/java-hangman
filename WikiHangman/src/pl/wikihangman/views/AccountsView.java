package pl.wikihangman.views;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import pl.wikihangman.controllers.AccountController;
import pl.wikihangman.models.User;

/**
 * {@code AccountsView} is used to allow user to manipulate user database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountsView {
    
    Logger logger = new Logger();
    
    public User displayLogInView() {
        
        AtomicBoolean retry = new AtomicBoolean(true);
        User user = null;
        UserActionReader actionReader = new UserActionReader();
        actionReader.setHeader("Try again?")
                    .addAction("yes", () -> retry.set(true))
                    .addAction("no", () -> retry.set(false));
        
        while(retry.get()) {
            boolean failure = false;
            
            AccountController accountController = new AccountController();
            UserInputReader inputReader = new UserInputReader();
            inputReader.addQuestion("User name")
                       .addQuestion("Password");
            
            List<Object> inputResult = inputReader.read();
            String userName = (String)inputResult.get(0);
            String password = (String)inputResult.get(1);
        
            try {
                user = accountController.authenticate(userName, password);
                if (user == null) {
                    failure = true;
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
