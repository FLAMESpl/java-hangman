package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;
import pl.wikihangman.server.services.WikipediaService;

/**
 * Handles new game requests. Returns new hangman to play and saves user's
 * session.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class StartCommand extends Command {

    private final static String COMMAND_NAME = "START";
    private final AtomicReference<User> activeUser;
    private final AtomicReference<Hangman> activeHangman;
    private final WikipediaService wikipediaService;
    
    /**
     * 
     * @param activeUser reference to active user
     * @param activeHangman reference to active hangman
     * @param wikipediaService service communicating with wikipedia's api
     */
    public StartCommand(AtomicReference<User> activeUser, 
            AtomicReference<Hangman> activeHangman, WikipediaService wikipediaService) {
        super(COMMAND_NAME);
        this.activeHangman = activeHangman;
        this.activeUser = activeUser;
        this.wikipediaService = wikipediaService;
    }
    
    /**
     * Creates new hangman only if there is active user for this client. This
     * command has no parameters.
     * 
     * @param options empty array
     * @return response message
     * @throws ServiceException if internal service has thrown exception
     */
    @Override
    public String execute(String[] options) throws ServiceException {
        
        if (activeUser.get() == null) {
            return fail("Previous user authentication is needed to perform this action");
        }
        int lives = Integer.parseInt(options[0]);
        Hangman hangman;
        try {
            hangman = wikipediaService.createHangman(lives);
            activeHangman.set(hangman);
        } catch (URISyntaxException | TimeoutException | InterruptedException | 
                 IOException exception) {
            throw new ServiceException(exception);
        }
        return success(hangman);
    }
    
    /**
     * 
     * @return information about this command usage
     */
    @Override
    public String usage() {
        return String.format(
            "Creates new hangman if user has logged in. Usage : %1$s <amount of lives>",
            getName());
    }
    
    /**
     * 
     * @param options command options to validate
     * @return validation result indicating if options are correct
     */
    @Override
    public ValidationResult validate(String[] options) {
        if (options.length != 1) {
            return ValidationResult.fail(getName() + " must have one argument.");
        }
        try {
            Integer.parseInt(options[0]);
        } catch (NumberFormatException exception) {
            return ValidationResult.fail(getName() + " argument must be integer.");
        }
        return ValidationResult.success();
    }
    
    /**
     * Creates success response with hangman parameters as single string formatted
     * as: SUCCESS [keyword's category] [keyword's length] [max lives].
     * 
     * @param hangman hangman to response
     * @return formatted success string
     */
    protected String success(Hangman hangman) {
        return success(Integer.toString(hangman.getKeywordsLength()), 
                       Integer.toString(hangman.getMaxLives()),
                       hangman.getArticleCategory());
    }
}
