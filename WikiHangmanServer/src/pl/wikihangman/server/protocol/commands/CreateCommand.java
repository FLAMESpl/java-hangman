package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import pl.wikihangman.server.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;
import pl.wikihangman.server.services.AccountsService;

/**
 * Handles new user creating requests.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class CreateCommand extends Command {

    private static final String COMMAND_NAME = "CREATE";
    private final String dbPath;
    private final AccountsService accountService;
    
    /**
     * 
     * @param dbPath path to users' database file
     */
    public CreateCommand(String dbPath) {
        super(COMMAND_NAME);
        this.dbPath = dbPath;
        this.accountService = new AccountsService(dbPath);
    }
    
    /**
     * Creates user with credentials given in parameters. Requires two arguments,
     * user's name and user's password. User's name must be unique.
     * 
     * @param options requires array of two elements - user name and password
     * @return response to the client
     * @throws ServiceException when internal service exception is thrown
     */
    @Override
    public String execute(String[] options) throws ServiceException {
        
        try {
            accountService.register(options[0], options[1]);
        } catch (EntityAlreadyExistsException | IOException exception) {
            throw new ServiceException(exception);
        }
        return success();
    }
    
    /**
     * 
     * @return details about usage of this command
     */
    @Override
    public String usage() {
        return "Creates new user in database. Usage : CREATE <username> <password>";
    }
    
    /**
     * 
     * @param options options that will be validated
     * @return validation result indicating if options are correct
     */
    @Override
    public ValidationResult validate(String[] options) {
        return options.length == 2 ?
            ValidationResult.success() :
            ValidationResult.fail(getName() + " must have 2 parameters.");
    }
}
