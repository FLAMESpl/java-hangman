package pl.wikihangman.server.protocol.commands;

import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;

/**
 * Handles logout command, sets active user and active hangman to nulls.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class LogoutCommand extends Command {

    private static final String COMMAND_NAME = "LOGOUT";
    private final AtomicReference<Hangman> activeHangman;
    private final AtomicReference<User> activeUser;
    
    public LogoutCommand(AtomicReference<User> activeUser, 
            AtomicReference<Hangman> activeHangman) {
        super(COMMAND_NAME);
        this.activeHangman = activeHangman;
        this.activeUser = activeUser;
    }
    
    /**
     * Executes logout command, sets active user and active hangman to nulls.
     * 
     * @param options 
     * @return response to the client
     */
    @Override
    public String execute(String[] options) {
        
        activeHangman.set(null);
        activeUser.set(null);
        return success();
    }
    
    /**
     * 
     * @return information about usage of this command
     */
    @Override
    public String usage() {
        return "Logs user out. Usage : " + COMMAND_NAME;
    }
    
    /**
     * 
     * @param options options to validate
     * @return validation result indicating if options are correct
     */
    @Override
    public ValidationResult validate(String[] options) {
       return options.length == 0 ?
            ValidationResult.success() :
            ValidationResult.fail(getName() + " has no parameters."); 
    }
}
