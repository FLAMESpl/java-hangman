package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
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
     * @throws CommandOptionsException 
     * @throws ServiceException 
     */
    @Override
    public String execute(String[] options) 
            throws CommandOptionsException, ServiceException {
        
        if (validate(options)) {
            return authenticate(options) ? success(activeUser.get()) : fail();
        } else {
            throw new CommandOptionsException(COMMAND_NAME + " requires two parameters");
        }
    }
    
    
    /**
     * 
     * @param options options that will be validated
     * @return true if options are valid, otherwise false
     */
    private boolean validate(String[] options) {
        return options.length == 2;
    }
    
    /**
     * Calls authentication service.
     * 
     * @param options authentication arguments
     * @return true if user has been logged in, otherwise false
     */
    private boolean authenticate(String[] options) throws ServiceException {
        boolean result = false;
        try {
            User user = accountsService.authenticate(options[0], options[1]);
            if (user != null) {
                result = true;
                activeUser.set(user);
            }
        } catch (IOException | NumberFormatException exception) {
            throw new ServiceException(exception);
        }
        return result;
    }
    
    /**
     * Creates success message with additional information of authenticated user.
     * 
     * @param user active user in the system
     * @return formatted success response message
     */
    protected String success(User user) {
        return String.format("%1$s %2$d %3$s %4$d", 
                success(), user.getId(), user.getName(), user.getPoints());
    }
}
