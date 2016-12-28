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
    private final WikipediaService wikiService;
    private final AtomicReference<Hangman> activeHangman;
    
    /**
     * 
     * @param wikiService service communicating with wikipedia's api
     * @param activeHangman reference to the active hangman
     */
    public InfoCommand(WikipediaService wikiService, AtomicReference<Hangman> activeHangman) {
        super(COMMAND_NAME);
        this.wikiService = wikiService;
        this.activeHangman = activeHangman;
    }

    @Override
    protected String execute(String[] options) throws ServiceException {
        
        try {
            if (activeHangman.get() != null) {
                String info = wikiService.getInfromationFromArticle(activeHangman.get());
                return success(info);
            } else {
                return fail();
            }
        } catch (URISyntaxException | IOException  exception) {
            throw new ServiceException(exception);
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
