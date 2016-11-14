package pl.wikihangman.views;

import java.io.IOException;
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
    
    private AccountController accountController;
    private User activeUser;
    
    public MasterView() {
        accountController = new AccountController();
        activeUser = null;
    }
    
    /**
     * Entry point for application, loops for reading user input.
     * 
     * @param applicationArgs command-line arguments of application
     */
    public void start(String[] applicationArgs) {
        
        try {
            activeUser = accountController.authenticate(applicationArgs[0], applicationArgs[1]);
        } catch(IOException ioException) {
            System.err.println("Database file error occured");
        } catch(IndexOutOfBoundsException indexException) {
            System.err.println("Index out of range error occured");
        } catch(NumberFormatException numberFormatException) {
            System.err.println("Number parsing error occured");
        }
        
        UserActionReader reader = new UserActionReader();
        reader.addAction("exit", null)
              .addAction("login", null)
              .addAction("singup", null);
    }
}
