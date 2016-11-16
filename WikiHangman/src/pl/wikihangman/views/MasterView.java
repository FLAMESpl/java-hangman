package pl.wikihangman.views;

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
    
    private AccountController accountController;
    private AccountsView accountsView;
    private User activeUser;
    
    public MasterView() {
        accountController = new AccountController();
        accountsView = new AccountsView();
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
        
        AtomicBoolean exit = new AtomicBoolean(false);
        UserActionReader reader = new UserActionReader();
        reader.setHeader("Available actions:")
              .addAction("exit", () -> exit.set(true))
              .addAction("login", () -> activeUser = accountsView.displayLogInView())
              .addAction("singup", null);
        
        while (!exit.get()) {
            reader.read();
        }
    }
}
