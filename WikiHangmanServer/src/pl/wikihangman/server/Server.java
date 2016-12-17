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
    private String dbPath;
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
     * 
     * @param dbPath path to user's database file
     * @return this object
     */
    public Server setDatabasePath(String dbPath) {
        this.dbPath = dbPath;
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
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket;
            while (true) {
                try {
                    socket = serverSocket.accept();
                    handleClient(socket);
                    logger.log("User has connected.");
                } catch (IOException ioException) {
                    logger.log("Client socket has failed to initialize.");
                }
            }
        } catch (IOException ioException) {
            logger.log("Server has failed to initialize. Closing...");
        }
        
    }
    
    /**
     * Sets and runs client handler in separate thread.
     * 
     * @param socket socket
     */
    private void handleClient(Socket socket) throws IOException {
        ClientHandler handler = new ClientHandler(socket, logger, dbPath);
        Thread thread = new Thread(handler);
        thread.start();
        clientHandlers.add(handler);
    }
}
