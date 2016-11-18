package pl.wikihangman.views;

import pl.wikihangman.services.*;

/**
 * Is used to initialize values of {@code ViewBase} inside constructor.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 * @see ViewBase
 */
public class ViewBaseConstructorDescriptor {
    
    private AccountsService accountService = null;
    private GameService gameService = null;
    private Logger logger = null;
    
    /**
     * 
     * @param service new account service
     * @return this object
     */
    public ViewBaseConstructorDescriptor setAccountService(AccountsService service) {
        accountService = service;
        return this;
    }
    
    /**
     * 
     * @param service new game service
     * @return this object
     */
    public ViewBaseConstructorDescriptor setGameService(GameService service) {
        gameService = service;
        return this;
    }
    
    /**
     * 
     * @param logger messages logger
     * @return this object
     */
    public ViewBaseConstructorDescriptor setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }
    
    /**
     * 
     * @return account service
     */
    public AccountsService getAccountService(){
        return accountService;
    }
    
    /**
     * 
     * @return game service
     */
    public GameService getGameService() {
        return gameService;
    }
    
    /**
     * 
     * @return message logger
     */
    public Logger getLogger() {
        return logger;
    }
}
