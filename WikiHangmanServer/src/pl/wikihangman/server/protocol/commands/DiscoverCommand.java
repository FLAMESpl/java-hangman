package pl.wikihangman.server.protocol.commands;

import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.Letter;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ProtocolCode;
import pl.wikihangman.server.protocol.ValidationResult;

/**
 * Handles requests to discover new letter in hangman and responses with
 * updated hangman view.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class DiscoverCommand extends Command {

    private static final String COMMAND_NAME = "DISCOVER";
    private final AtomicReference<Hangman> activeHangman;
    private final AtomicReference<User> activeUser;
    
    public DiscoverCommand(AtomicReference<Hangman> activeHangman,
            AtomicReference<User> activeUser) {
        super(COMMAND_NAME);
        this.activeHangman = activeHangman;
        this.activeUser = activeUser;
    }
    
    /**
     * Discovers letter if its present in hangman and updates model's state.
     * Letter is passed as first parameter and it is optional. If no letter
     * is provided then hangman state is resent unchanged. User must be
     * logged into system and hangman must be previously created.
     * 
     * @param options one optional parameter which is letter to discover
     * @return actual hangman state formatted as single string
     * @throws ServiceException when inner service has thrown an exception
     */
    @Override
    public String execute(String[] options) throws ServiceException {
        
        if (activeUser.get() == null) {
            return fail("Previous user authentication is needed to perform this action.");
        }
        
        if (activeHangman.get() == null) {
            return fail("Hangman must be started first to perform this action");
        }
            
        if (options.length == 1) {
                activeHangman.get().discover(options[0].charAt(0));
        }
        
        return success();
    }
    
    /**
     * 
     * @return information about command's usage
     */
    @Override
    public String usage() {
        return String.format("Discovers letter in hangman if one is present, " +
                "otherwise hangman is left unchanged. Usage : %1$s <optional letter>",
                COMMAND_NAME);
    }
    
    /**
     * 
     * @param options options to validate
     * @return validation result indicating if options are correct
     */
    @Override
    public ValidationResult validate(String[] options) {
        return options.length <= 1 ?
            ValidationResult.success() :
            ValidationResult.fail(getName() + " can have only one optional parameter.");
    }
    
    /**
     * Formats hangman information to single string and appends it to 
     * success response.
     * 
     * @return success response with hangman state
     */
    @Override
    protected String success() {
        Hangman hangman = activeHangman.get();
        StringBuilder encryptedKeyword = new StringBuilder();
        for (Letter letter : hangman.getKeyword()) {
            encryptedKeyword.append(letter.isDiscovered() ? 
                    letter.getCharacter() : ProtocolCode.ofBoolean(false));
            encryptedKeyword.append(" ");
        }
        return String.format("%1$s %2$s %3$d %4$d", super.success(), 
                encryptedKeyword, hangman.getMaxLives(), hangman.getActualLives());
    }
}
