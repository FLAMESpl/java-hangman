package pl.wikihangman.views.swing.events;

import java.util.EventListener;

/**
 * Listens for any user's attempt to log in.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public interface LogInAttemptListener extends EventListener {

                
    /**
     * Invoked when user attempted to log in.
     * 
     * @param logInAttemptEvent arguments regarding log in event
     */
    public void attemptedToLogIn(LogInAttemptEvent logInAttemptEvent);
}
