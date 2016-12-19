package pl.wikihangman.server.protocol.commands;

import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;

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
     * @throws CommandOptionsException when command options are invalid
     */
    @Override
    public String execute(String[] options) throws CommandOptionsException {
        if (validate(options)) {
            activeHangman.set(null);
            activeUser.set(null);
            return success();
        } else {
            throw new CommandOptionsException(COMMAND_NAME + " has no parameters");
        }
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
     * @return true if options are valid, otherwise false
     */
    private boolean validate(String[] options) {
       return options.length == 0; 
    }
}
