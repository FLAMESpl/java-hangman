package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;
import pl.wikihangman.server.services.AccountsService;

/**
 * Handles authentication requests.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AuthCommand extends Command {

    private static final String COMMAND_NAME = "AUTH";
    private final AtomicReference<User> activeUser;
    private final AccountsService accountsService;
    
    /**
     * 
     * @param activeUser reference to active user in the system
     * @param dbPath path to the user's database file
     */
    public AuthCommand(AtomicReference<User> activeUser, String dbPath) {
        super(COMMAND_NAME);
        this.activeUser = activeUser;
        this.accountsService = new AccountsService(dbPath);
    }
    
    /**
     * Authenticates user by credentials given in parameter string. Requires two 
     * options that first is user's name and second is user's password. If
     * process was successful, active user is set.
     * 
     * @param options requires array of two elements - user name and password
     * @return response to the client
     * @throws ServiceException 
     */
    @Override
    public String execute(String[] options) throws ServiceException {
        
        if (activeUser.get() != null) {
            return fail("Active user must log out first.");
        }
        
        try {
            User user = accountsService.authenticate(options[0], options[1]);
            if (user != null) {
                activeUser.set(user);
                return success();
            } else {
                return fail("Incorrect credentials.");
            }
        } catch (IOException | NumberFormatException exception) {
            throw new ServiceException(exception);
        }
    }
    
    /**
     * 
     * @return details about usage of this command
     */
    @Override
    public String usage() {
        return "Log user into system. Usage : AUTH <username> <password>";
    }
    
    /**
     * @param options options that will be validated
     * @return validation result indicating if options are correct
     */
    @Override
    public ValidationResult validate(String[] options) {
        return options.length == 2 ?
            ValidationResult.success() :
            ValidationResult.fail(getName() + " must have 2 parameters.");
    }
    
    /**
     * Creates success message with additional information of authenticated user.
     * 
     * @return formatted success response message
     */
    @Override
    protected String success() {
        User user = activeUser.get();
        return success(Integer.toString(user.getId()), user.getName(),
                Long.toString(user.getPoints()));
    }
}
