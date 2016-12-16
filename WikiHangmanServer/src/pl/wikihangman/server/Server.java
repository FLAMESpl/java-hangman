package pl.wikihangman.server;

import pl.wikihangman.server.logging.ServerLogger;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Server {

    private int port;
    private ServerLogger logger = new ServerLogger(System.err);
    
    /**
     * 
     * @param port default port to listen on    
     * @return this object
     */
    public Server setDefaultPort(int port) {
        this.port = port;
        return this;
    }
    
    /**
     * Starts listening for clients on a default port.
     */
    public void start() {
        
        ServerSocket socket;
        while (true) {
            try {
                socket = new ServerSocket(port);
                socket.accept();
            } catch (IOException ioException) {
                
            }
        }
    }
}
