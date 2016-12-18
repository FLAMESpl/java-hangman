package pl.wikihangman.server.protocol;

/**
 * Contains keywords of server protocol messages.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum Protocol {

    SUCCESS("SUCCESS"),
    FAIL("FAIL"),
    EXCEPTION("EXCEPTION");
    
    private final String keyword;
    
    private Protocol(String keyword) {
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
