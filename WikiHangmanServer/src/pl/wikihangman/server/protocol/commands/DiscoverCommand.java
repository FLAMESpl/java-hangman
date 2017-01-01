package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.EntityDoesNotExistException;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.Letter;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.protocol.ProtocolCode;
import pl.wikihangman.server.protocol.ValidationResult;
import pl.wikihangman.server.services.AccountsService;

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
    private final AccountsService accountService;
    
    public DiscoverCommand(AtomicReference<Hangman> activeHangman,
            AtomicReference<User> activeUser, String dbPath) {
        super(COMMAND_NAME);
        this.activeHangman = activeHangman;
        this.activeUser = activeUser;
        this.accountService = new AccountsService(dbPath);
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
        
        Hangman hangman = activeHangman.get();
        User user = activeUser.get();
        
        if (activeUser.get() == null) {
            return fail("Previous user authentication is needed to perform this action.");
        }
        
        if (hangman == null) {
            return fail("Hangman must be started first to perform this action.");
        }
            
        if (options.length == 1) {
            if (!hangman.hasAnyLivesLeft()) {
                return fail("Hangman ran out of lives.");
            }
            if (!hangman.hasUndiscoveredLetters()) {
                return fail("All leters have been discovered already.");
            }
            activeHangman.get().discover(options[0].charAt(0));
            if (hangman.hasAnyLivesLeft() && !hangman.hasUndiscoveredLetters()) {
                user.score(hangman.getMaxLives());
                try {
                    accountService.update(user);
                } catch (EntityDoesNotExistException | IOException exception) {
                    throw new ServiceException(exception);
                }
            }
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
        boolean gameOver = !hangman.hasAnyLivesLeft() || !hangman.hasUndiscoveredLetters();
        return String.format("%1$s %2$s %3$d %4$d %5$s", 
                super.success(), ProtocolCode.ofBoolean(gameOver),
                hangman.getMaxLives(), hangman.getActualLives(), encryptedKeyword);
    }
}
