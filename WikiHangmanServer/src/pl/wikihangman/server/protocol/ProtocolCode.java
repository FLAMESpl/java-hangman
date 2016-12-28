package pl.wikihangman.server.protocol;

/**
 * Translates common objects to server protocol codes.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ProtocolCode {
    
    private static final String BOOLEAN_TRUE = "TRUE";
    private static final String BOOLEAN_FALSE = "FALSE";

    /**
     * Translates boolean value to server protocol code.
     * 
     * @param booleanValue boolean value
     * @return boolean text code
     */
    public static String ofBoolean(boolean booleanValue) {
        return booleanValue ? BOOLEAN_TRUE : BOOLEAN_FALSE;
    }
}
