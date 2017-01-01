package pl.wikihangman.protocol;

/**
 * Contains keywords of server protocol response headers.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum ProtocolResponse {

    SUCCESS("SUCCESS"),
    FAIL("FAIL"),
    EXCEPTION("EXCEPTION");
    
    private final String keyword;
    
    private ProtocolResponse(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * 
     * @return name of this protocol's keyword
     */
    public String getName() {
        return keyword;
    }
    
    public static ProtocolResponse parse(String response) 
            throws ProtocolParseException {
        
        ProtocolResponse[] protocolResponses = values();
        int length = protocolResponses.length;
        int i;
        for (i = 0; i < length; i++) {
            if (protocolResponses[i].getName().equals(response)) {
                break;
            }
        }
        if (i == length) {
            throw new ProtocolParseException(response, "protocol response");
        }
        return protocolResponses[i];
    }
}
