package pl.wikihangman.server;

/**
 * Entry point for server application.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikiHangmanServer {

    public static final int PORT = 8888;
    public static final String DB_PATH = ".\\db.txt";
    public static final String LOG_OUTPUT_FILE_PATH = ".\\logs.txt";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Server server = new Server()
                .setDefaultPort(PORT)
                .setDatabasePath(DB_PATH)
                .setLogOutputFile(LOG_OUTPUT_FILE_PATH);
        
        server.start();
    }
}
