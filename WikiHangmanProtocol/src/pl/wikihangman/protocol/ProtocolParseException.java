package pl.wikihangman.protocol;

/**
 * Signals that could not parse string code to type value
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ProtocolParseException extends Exception {
    
    /**
     * 
     * @param inputString string that could not be parsed
     * @param outputType target type
     */
    public ProtocolParseException(String inputString, String outputType) {
        super(String.format("Could not parse %1$s to %2$s", inputString, outputType));
    }
}
