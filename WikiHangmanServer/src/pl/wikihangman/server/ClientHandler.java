package pl.wikihangman.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.ServerException;
import pl.wikihangman.server.logging.ServerLogger;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.CommandResolver;
import pl.wikihangman.server.protocol.commands.AuthCommand;

/**
 * Receives and processes command issued by connected client and respondes with
 * computated results.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final CommandResolver commandResolver;
    private final String dbPath;
    private final ServerLogger logger;
    
    private final AtomicReference<User> activeUser;
    
    /**
     * 
     * @param socket server's socket user has connected to
     * @param logger server message's logger
     * @param dbPath path to database
     * @throws IOException
     */
    public ClientHandler(Socket socket, ServerLogger logger, String dbPath) 
            throws IOException {
        
        this.socket = socket;
        this.dbPath = dbPath;
        this.logger = logger;
        
        out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        
        activeUser = new AtomicReference<>(null);
        
        commandResolver = new CommandResolver()
                .addCommand(new AuthCommand(activeUser, dbPath));
    }
    
    /**
     * Runs {@code ClientHandler} making it ready for client's requests.
     */
    @Override
    public void run() {
        
        try {
            String request, response;
            while (true) {
                request = in.readLine();
                logger.log(logRequest(request));
                try {
                    response = commandResolver.resolve(request);
                } catch (ServerException exception) {
                    response = exception.getMessage();
                }
                out.println(response);
                logger.log(logResponse(response));
            }
        } catch (IOException ioException) {
            
        }
    }
    
    /**
     * Wraps logic of constructing request message for server logger.
     * 
     * @param request client request message
     * @return formatted message to log
     */
    private String logRequest(String request) {
        return String.format("Client requests %1$s `%2$s`", logWithActiveUser(), request);
    }
    
    /**
     * Wraps logic of constructing response message for server logger.
     * 
     * @param response server response message
     * @return formtted message to log
     */
    private String logResponse(String response) {
        return String.format("Server responses %1$s `%2$s`", logWithActiveUser(), response);
    }
    
    /**
     * Creates part of formatted message with user name if there is active one.
     * 
     * @return part of formatted log message
     */
    private String logWithActiveUser() {
        return activeUser.get() != null ? "(" + activeUser.get().getName() + ") :" : ":";
    }
}
