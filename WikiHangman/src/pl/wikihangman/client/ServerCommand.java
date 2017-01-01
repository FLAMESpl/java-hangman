package pl.wikihangman.client;

/**
 * Commands recognized by server.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum ServerCommand {

    AUTH("AUTH"),
    CREATE("CREATE"),
    DISCOVER("DISCOVER"),
    HELP("HELP"),
    INFO("INFO"),
    LIST("LIST"),
    LOGOUT("LOGOUT"),
    START("START");
    
    private final String name;
    
    /**
     * 
     * @param name name of command
     */
    private ServerCommand(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return name of command
     */
    public String getName() {
        return name;
    }
}
