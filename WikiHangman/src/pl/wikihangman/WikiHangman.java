package pl.wikihangman;

import pl.wikihangman.services.AccountsService;
import pl.wikihangman.controllers.*;
import pl.wikihangman.views.MasterView;


/**
 * Entry point for an application.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikiHangman {
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AccountsService accountController = new AccountsService();
        GameController gameController = new GameController();
        
        MasterView masterView = new MasterView(v -> v
                .setAccountService(accountController)
                .setGameService(gameController));
                
        masterView.start(args);
    }
}
