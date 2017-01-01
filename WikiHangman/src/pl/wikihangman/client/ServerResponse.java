package pl.wikihangman.client;

import java.util.Arrays;
import pl.wikihangman.protocol.*;

/**
 * Response from server separated to logical parts.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ServerResponse {
    
    private final ProtocolResponse responseType;
    private final String[] responseTokens;
    private final String responseMessage;
    
    /**
     * 
     * @param response text line response from server
     * @throws ProtocolParseException if could not identify response code
     * @throws IndexOutOfBoundsException if response contains nothing more than
     *  whitespaces
     */
    public ServerResponse(String response) 
            throws ProtocolParseException, IndexOutOfBoundsException {
        
        String[] tokens = response.split(" ");
        responseTokens = Arrays.stream(tokens).skip(1).toArray(String[]::new);
        responseType = ProtocolResponse.parse(tokens[0]);
        responseMessage = String.join(" ", responseTokens);
    }
    
    /**
     * 
     * @return response message with skipped {@code ProtocolResponse} code
     */
    public String getResponseMessage() {
        return responseMessage;
    }
            
    /**
     * 
     * @param index index of token
     * @return token from response of given index as string
     */
    public String getStringToken(int index) {
        return responseTokens[index];
    }
    
     /**
     * 
     * @param index index of token
     * @return token from response of given index as long
     */
    public long getLongToken(int index) {
        return Long.parseLong(responseTokens[index]);
    }
    
    /**
     * 
     * @param index index of token
     * @return token from response of given index as int
     */
    public int getIntToken(int index) {
        return Integer.parseInt(responseTokens[index]);
    }
    
    /**
     * 
     * @param index index of token
     * @return token from response of given index as boolean
     * @throws ProtocolParseException 
     */
    public boolean getBoolToken(int index) throws ProtocolParseException {
        return ProtocolCode.parseBoolean(responseTokens[index]);
    }
    
    /**
     * 
     * @return type of response
     */
    public ProtocolResponse getResponseType() {
        return responseType;
    }
    
    /**
     * 
     * @return amount of tokens except protocol code
     */
    public int getLength() {
        return responseTokens.length;
    }
}
