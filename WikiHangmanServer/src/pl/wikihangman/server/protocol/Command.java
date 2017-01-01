package pl.wikihangman.server.protocol;

import pl.wikihangman.protocol.ProtocolResponse;
import pl.wikihangman.server.exceptions.CommandOptionsException;
import pl.wikihangman.server.exceptions.ServiceException;

/**
 * Command recognizable and executable by server's api.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public abstract class Command {

    private final String name;
    
    /**
     * 
     * @param name invokable name of this command
     */
    public Command(String name) {
        this.name = name;
    }
    
    /**
     * Resolves command and its parameters by validating them, and if they are
     * correct {@code execute} function of this class is invoked.
     * 
     * @param options options passed to this command
     * @return response to the client
     * @throws CommandOptionsException when commands are invalid
     * @throws ServiceException when service used by this command throws an exception
     */
    public final String resolve(String[] options) 
        throws CommandOptionsException, ServiceException {
        
        ValidationResult result = validate(options);
        if (result.isValid()) {
            return execute(options);
        } else {
            throw new CommandOptionsException(
                    ProtocolResponse.EXCEPTION.getName() + " " + result.getReason());
        }
    }
    
    /**
     * Executes this command with given options assuming they are correct.
     * 
     * @param options options passed to this command
     * @return response to the client
     * @throws ServiceException when service used by this command throws an exception
     */
    protected abstract String execute(String[] options) throws ServiceException;
    
    /**
     * 
     * @return datails about usage of this command
     */
    public abstract String usage();
    
    /**
     * 
     * @param options
     * @return 
     */
    public abstract ValidationResult validate(String[] options);
    
    /**
     * 
     * @return string that invokes this command
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return `success` protocol keyword
     */
    protected String success() {
        return ProtocolResponse.SUCCESS.getName();
    }
    
    /**
     * 
     * @return `fail` protocol keyword
     */
    protected String fail() {
        return ProtocolResponse.FAIL.getName();
    }
    
    /**
     * Generates `success` response with additional text information specified
     * by list of strings. Each argument is separated by space.
     * 
     * @param arguments arguments to be added to `success` message
     * @return `success` response with concatenated information
     */
    protected String success(String... arguments) {
        return formatArguments(ProtocolResponse.SUCCESS, arguments);
    }
    
    /**
     * Generates `fail` response with additional text information specified
     * by list of strings. Each argument is separated by space.
     * 
     * @param arguments arguments to be added to `fail` message
     * @return `fail` response with concatenated information
     */
    protected String fail(String... arguments) {
        return formatArguments(ProtocolResponse.FAIL, arguments);
    }
    
    /**
     * Formats array of strings to single response string.
     * 
     * @param responseType type {@code Protocol} response
     * @param arguments arguments to be joined
     * @return array joined with response type name to single string
     */
    private String formatArguments(ProtocolResponse responseType, String... arguments) {
        return responseType.getName() + " " + String.join(" ", arguments);
    }
}
