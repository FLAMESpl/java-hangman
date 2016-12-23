package pl.wikihangman.server.protocol.commands;

import java.util.Map;
import java.util.function.Supplier;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.protocol.Command;
import pl.wikihangman.server.protocol.ValidationResult;

/**
 * Handles help requests by printing all available commands in the system.
 * List of command names is inferred from commands resolver's map.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class HelpCommand extends Command {
    
    private static final String COMMAND_NAME = "HELP";
    private final Supplier<Map<String, Command>> commandsSource;
    
    /**
     * 
     * @param commandsSource function providing map of names and commands 
     *  in the system
     */
    public HelpCommand(Supplier<Map<String, Command>> commandsSource) {
        super(COMMAND_NAME);
        this.commandsSource = commandsSource;
    }
    
    /**
     * Returns help information based on command paramters. If there is one
     * returns information about commands usage of name in help's parameter,
     * otherwise returns list of all available commands.
     * 
     * @param options no arguments or name of command in the system
     * @return response containing list of all available commands
     * @throws ServiceException when internal service has thrown an exception
     */
    @Override
    public String execute(String[] options) throws ServiceException {
        
        String info;
        Map<String, Command> commands = commandsSource.get();
        if (options.length == 0) {
            info = String.join(" ", commands.keySet());
        } else {
            Command command = commands.get(options[0].toUpperCase());
            info = command != null ? command.usage() : null;
        }
        return info != null ? 
                success(info) : 
                fail(getName(), " is not recognizable command name.");
    }
    
    /**
     * 
     * @return information about usage of this command
     */
    @Override
    public String usage() {
        return "Lists all available commands or command informtion. Usage : HELP <optional command name>";
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
}
