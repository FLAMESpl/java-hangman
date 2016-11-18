package pl.wikihangman;

import pl.wikihangman.services.*;
import pl.wikihangman.views.AccountsView;
import pl.wikihangman.views.Logger;


/**
 * Entry point for an application.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikiHangman {
            
    private static final String DB_PATH = ".\\db.txt";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AccountsService accountService = new AccountsService(DB_PATH);
        GameService gameServices = new GameService();
        Logger logger = new Logger();
        
        AccountsView accountsView = new AccountsView(v -> v
                .setAccountService(accountService)
                .setGameService(gameServices)
                .setLogger(logger));
                
        accountsView.start(args);
    }
}
