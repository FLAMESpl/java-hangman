package pl.wikihangman.server.protocol.commands;

import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;

/**
 * Handles new game requests. Returns new hangman to play and saves user's
 * session.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class StartCommand extends Command {

    private static final String COMMAND_NAME = "START";
    private final AtomicReference<User> activeUser;
    private final AtomicReference<Hangman> activeHangman;
    
    /**
     * 
     * @param activeUser reference to active user
     * @param activeHangman reference to active hangman
     */
    public StartCommand(AtomicReference<User> activeUser, 
            AtomicReference<Hangman> activeHangman) {
        super(COMMAND_NAME);
        this.activeHangman = activeHangman;
        this.activeUser = activeUser;
    }
    
    /**
     * Creates new hangman only if there is active user for this client. This
     * command has no parameters.
     * 
     * @param options empty array
     * @return response message
     * @throws CommandOptionsException when command's options are not valid
     * @throws ServiceException when internal service has thrown an exception
     */
    @Override
    public String execute(String[] options) 
            throws CommandOptionsException, ServiceException {
        
        if (validate(options)) {
            Hangman hangman = startHangman();
            return hangman != null ? success(hangman) : fail();
        } else {
            throw new CommandOptionsException(COMMAND_NAME + " has no parameters");
        }
    }
    
    /**
     * 
     * @return information about this command usage
     */
    @Override
    public String usage() {
        return "Creates new hangman if user has logged in. Usage : " + COMMAND_NAME;
    }
    
    /**
     * 
     * @param options command options to validate
     * @return true if options are valid, otherwise false
     */
    private boolean validate(String[] options) {
        return options.length == 0;
    }
    
    /**
     * Creates new hangman if there is active user in client handler.
     * 
     * @return new hangman if client is active, otherwise null
     */
    private Hangman startHangman() {
        
        Hangman hangman;
        if (activeUser.get() != null) {
            hangman = new Hangman()
                    .createKeyword("Testowy")
                    .setActualLives(6)
                    .setMaxLives(6);
            activeHangman.set(hangman);
        } else {
            hangman = null;
        }
        return hangman;
    }
    
    /**
     * Creates success response with hangman paramters as single string formatted
     * as: SUCCESS [keyword's length] [max lives].
     * 
     * @param hangman hangman to response
     * @return formatted success string
     */
    protected String success(Hangman hangman) {
        return String.format("%1$s %2$d %3$d", 
                success(), hangman.getKeywordsLength(), hangman.getMaxLives());
    }
}
