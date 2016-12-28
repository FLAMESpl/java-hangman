package pl.wikihangman.server.protocol;

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
}
