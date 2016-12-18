package pl.wikihangman.server.protocol.commands;

import java.io.IOException;
import java.util.List;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.services.AccountsService;

/**
 * Handles players list requests.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ListCommand extends Command {
    
    private static final String COMMAND_NAME = "LIST";
    private final AccountsService accountsService;
    
    /**
     * 
     * @param dbPath path to user's database file
     */
    public ListCommand(String dbPath) {
        super(COMMAND_NAME);
        this.accountsService = new AccountsService(dbPath);
    }
    
    /**
     * Responses with array of players list. This command has no options,
     * so empty array is required.
     * 
     * @param options empty array
     * @return players' scores
     * @throws ServiceException when inner service throws an exception
     * @throws CommandOptionsException when command's options are not valid
     */
    @Override
    public String execute(String[] options) 
        throws ServiceException, CommandOptionsException {
        
        if (validate(options)) {
            List<User> users = getList();
            return success(users);
        } else {
            throw new CommandOptionsException(COMMAND_NAME + " has no parameters.");
        }
    }
    
    /**
     * 
     * @return datails about usage of this command
     */
    @Override
    public String usage() {
        return "Returns all players list from database. Usage : LIST";
    }
    
    /**
     * 
     * @param options command's options to validate
     * @return true if options are valid, otherwise false
     */
    private boolean validate(String[] options) {
        return options.length == 0;
    }
    
    /**
     * Uses {@code AccountsService} in order to get players list.
     * 
     * @return list of all players in database
     * @throws ServiceException when inner service throws an exception
     */
    private List<User> getList() throws ServiceException {
        try {
            return accountsService.getPlayersList();
        } catch (IOException | NumberFormatException exception) {
            throw new ServiceException(exception);
        }
    }
    
    /**
     * Formats players list to single reponse string.
     * 
     * @param users players list
     * @return success protocol's keyword with additional parameter of players list
     */
    private String success(List<User> users) {
        String response = success() + " ";
        for (User user : users) {
            response += String.format("%1$d %2$s %3$d ", 
                    user.getId(),user.getName(), user.getPoints());
        }
        return response;
    }
}
