package pl.wikihangman.protocol;

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
    
    /**
     * Parses text value to boolean value if possible, otherwise throws exception
     * 
     * @param text
     * @return parsed boolean
     * @throws ProtocolParseException 
     */
    public static boolean parseBoolean(String text) throws ProtocolParseException {
        if (text.equals(BOOLEAN_TRUE)) {
            return true;
        }
        if (text.equals(BOOLEAN_FALSE)) {
            return false;
        }
        throw new ProtocolParseException(text, "boolean");
    }
}
