package pl.wikihangman.server.protocol.commands;

import java.util.Map;
import java.util.function.Supplier;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.ServiceException;
import pl.wikihangman.server.protocol.Command;

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
     * @return response contining list of all available commands
     * @throws CommandOptionsException when command options are not valid
     * @throws ServiceException when internal service has thrown an exception
     */
    @Override
    public String execute(String[] options) 
            throws CommandOptionsException, ServiceException {
        
        if (validate(options)) {
            String info = helpInformation(options);
            return info != null ? success(info) : fail();
        } else {
            throw new CommandOptionsException(COMMAND_NAME + " can have only one optional parameter.");
        }
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
     * @return true if options are valid, otherwise false
     */
    private boolean validate(String[] options) {
        return options.length <= 1;
    }
    
    /**
     * Returns information about usage of command of namge given in paramter if
     * there is any, otherwise returns list of all available commands. If command
     * is not matched returns null.
     * 
     * @return help information based on paramters
     */
    private String helpInformation(String[] options) {
        String info;
        Map<String, Command> commands = commandsSource.get();
        if (options.length == 0) {
            info = String.join(" ", commands.keySet());
        } else {
            Command command = commands.get(options[0].toUpperCase());
            info = command != null ? command.usage() : null;
        }
        return info;
    }
    
    /**
     * 
     * @param helpInformation information based on help command paramter
     * @return formatted success response with helpInformation as its parameter
     */
    protected String success(String helpInformation) {
        return success() + " " + helpInformation;
    }
}
