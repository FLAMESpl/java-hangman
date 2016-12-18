package pl.wikihangman.server.protocol;

import java.util.HashMap;
import java.util.Map;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.NotSuchACommandException;
import pl.wikihangman.server.exceptions.ServiceException;

/**
 * Analyzes command string and determines specyfied actions.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class CommandResolver {

    /**
     * Recognizable commands in system.
     */
    private final Map<String, ICommand> commands = new HashMap<>();
    
    /**
     * Registers recognizable command into system.
     * 
     * @param command command to add to the system
     * @return this object
     */
    public CommandResolver addCommand(ICommand command) {
        commands.put(command.getName().toUpperCase(), command);
        return this;
    }
    
    /**
     * Resolves action specified in parameter. Command must be previously added
     * to this class'es object.
     * 
     * @param commandString text line of command with its options
     * @return response to the client
     * @throws NotSuchACommandException when command name is not matched
     * @throws CommandOptionsException when command's options are invalid
     * @throws ServiceException when service used by command threw an exception
     */
    public String resolve(String commandString) throws NotSuchACommandException,
            CommandOptionsException, ServiceException {
        
        String[] tokens = commandString.split(" ");
        if (tokens.length < 1) {
            return "";
        }
        
        String commandName = tokens[0].toUpperCase();
        ICommand command = commands.get(commandName);
        if (command != null) {
            return command.execute(commandsOptions(tokens));
        } else {
            throw new NotSuchACommandException(commandName);
        }
    }
    
    /**
     * Streams command's options from user input form, what means it skips first
     * element as it is command's name.
     * 
     * @param command tokens of full commnd text line
     * @return command's options array
     */
    private String[] commandsOptions(String[] command) {
        
        int newSize = command.length - 1;
        String[] result = new String[newSize];
        for (int i = 0; i < newSize; i++) {
            result[i] = command[i+1];
        }
        return result;
    }
}
