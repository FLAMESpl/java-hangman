package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;
import pl.wikihangman.server.services.WikipediaService;

/**
 * Handles requests of obtaining information about keyword from wikipedia.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class InfoCommand extends Command {

    private final static String COMMAND_NAME = "INFO";
    private final AtomicReference<Hangman> activeHangman;
    
    /**
     * 
     * @param activeHangman reference to the active hangman
     */
    public InfoCommand(AtomicReference<Hangman> activeHangman) {
        super(COMMAND_NAME);
        this.activeHangman = activeHangman;
    }

    /**
     * Returns information about hangman keyword if game is over.
     * 
     * @param options command's options
     * @return information about hangman keyword
     */
    @Override
    protected String execute(String[] options) {
        
        Hangman hangman = activeHangman.get();
        if (hangman != null) {
            if (hangman.hasUndiscoveredLetters() && hangman.hasAnyLivesLeft()) {
                return fail("Hangman must be completed first or all lives lost.");
            }
            String info = hangman.getArticleInformation();
            info = info.replace("\n", " ");
            return success(info);
        } else {
            return fail("Hangman must be started first.");
        }
    }

    /**
     * 
     * @return information about usage of this command
     */
    @Override
    public String usage() {
        return String.format("Returns information about keyword from wikipedia. Usage: %1$s", getName());
    }

    /**
     * 
     * @param options options to validate
     * @return information if options are valid
     */
    @Override
    public ValidationResult validate(String[] options) {
        return options.length == 0 ?
                ValidationResult.success() :
                ValidationResult.fail(getName() + " has no parameters.");
    }

}
