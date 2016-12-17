package pl.wikihangman.server.protocol;

import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.ServiceException;


/**
 * Command recognizable and executable by server's api.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public interface ICommand {

    /**
     * Executes this command with given options.
     * 
     * @param options options passed to this command
     * @return response to the client
     * @throws CommandOptionsException when commands are invalid
     * @throws ServiceException when service used by this command throws an exception
     */
    public String execute(String[] options) 
            throws CommandOptionsException, ServiceException;
    
    /**
     * 
     * @return string that invokes this command
     */
    public String getName();
}
