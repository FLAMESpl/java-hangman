package pl.wikihangman.server.protocol.commands;

import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;

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
     */
    @Override
    public String execute(String[] options) {
        
        if (activeUser.get() == null) {
            return fail("Previous user authentication is needed to perform this action");
        }
        Hangman hangman = new Hangman()
                    .createKeyword("Testowy")
                    .setActualLives(6)
                    .setMaxLives(6);
            activeHangman.set(hangman);
        return success(hangman);
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
     * @return validation result indicating if options are correct
     */
    @Override
    public ValidationResult validate(String[] options) {
        return options.length == 0 ?
            ValidationResult.success() :
            ValidationResult.fail(getName() + " has no arguments");
    }
    
    /**
     * Creates success response with hangman parameters as single string formatted
     * as: SUCCESS [keyword's length] [max lives].
     * 
     * @param hangman hangman to response
     * @return formatted success string
     */
    protected String success(Hangman hangman) {
        return success(Integer.toString(hangman.getKeywordsLength()), 
                       Integer.toString(hangman.getMaxLives()));
    }
}
