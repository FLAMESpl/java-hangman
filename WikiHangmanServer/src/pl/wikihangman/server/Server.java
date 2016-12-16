package pl.wikihangman.server;

import pl.wikihangman.server.logging.ServerLogger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class Server {

    private int port;
    private ServerLogger logger = new ServerLogger(System.err);
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    
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
     * Sets file to which logging output will be saved.
     * 
     * @param path path to the output file
     * @return this object
     */
    public Server setLogOutputFile(String path) {
        logger.outputToFile(path);
        return this;
    }
    
    /**
     * Starts listening for clients on a default port.
     */
    public void start() {
        
        logger.log("Server has started.");
        Socket socket;
        while (true) {
            try {
                socket = new ServerSocket(port).accept();
                logger.log("User has connected.");
                handleClient(socket);
            } catch (IOException ioException) {
                logger.log("Socket has failed to initialize.");
            }
        }
    }
    
    private void handleClient(Socket socket) {
        ClientHandler handler = new ClientHandler();
        clientHandlers.add(handler);
    }
}
