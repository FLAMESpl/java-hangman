package pl.wikihangman.views;

import java.util.function.Consumer;
import pl.wikihangman.services.*;

/**
 * Abstraction of any application's view. Provides complete set of services
 * with individual getters for each one.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public abstract class ViewBase {
    
    private final AccountsService accountController;
    private final GameService gameController;
    
    /**
     * Copies all services from its parent.
     * 
     * @param parent calling view
     */
    public ViewBase(ViewBase parent) {
        accountController = parent.accountController;
        gameController = parent.gameController;
    }
    
    /**
     * Initializes new services.
     * 
     * @param constructorDescriptorConsumer {@code FunctionalInterface}
     *      initializing controllers
     */
    public ViewBase(Consumer<ViewBaseConstructorDescriptor> constructorDescriptorConsumer) {
        ViewBaseConstructorDescriptor constructorDescriptor = new ViewBaseConstructorDescriptor();
        constructorDescriptorConsumer.accept(constructorDescriptor);
        accountController = constructorDescriptor.getAccountService();
        gameController = constructorDescriptor.getGameService();
    }
    
    /**
     * 
     * @return account service
     */
    protected AccountsService getAccountService() {
        return accountController;
    }
    
    /**
     * 
     * @return game service
     */
    protected GameService getGameService() {
        return gameController;
    }
}
