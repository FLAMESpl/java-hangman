package pl.wikihangman.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
import pl.wikihangman.server.exceptions.ServerException;
import pl.wikihangman.server.logging.ServerLogger;
import pl.wikihangman.server.models.Hangman;
import pl.wikihangman.server.models.User;
import pl.wikihangman.server.protocol.CommandResolver;
import pl.wikihangman.server.protocol.ProtocolResponse;
import pl.wikihangman.server.protocol.commands.*;
import pl.wikihangman.server.services.WikipediaService;

/**
 * Receives and processes command issued by connected client and responds with
 * computed results.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class ClientHandler implements Runnable {

    private final static Character[] AVAILABLE_CHARS = {
        'A', 'Ą', 'B', 'C', 'Ć', 'D', 'E', 'Ę', 'F', 'G', 'H', 'I', 'J', 'K', 
        'L', 'Ł', 'M', 'N', 'Ń', 'O', 'Ó', 'P', 'Q', 'R', 'S', 'Ś', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', 'Ź', 'Ż'
    };
    private final static int MAX_LENGTH = 15;
    private final static int MAX_RETRIES = 5;
    private final static int TIMEOUT = 1000;
    private final static int QUERY_SIZE = 10;
    private final static String WIKI_HOST = "pl.wikipedia.org";
    
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final CommandResolver commandResolver;
    private final String dbPath;
    private final ServerLogger logger;
    
    private final AtomicReference<User> activeUser;
    private final AtomicReference<Hangman> activeHangman;
    
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
        activeHangman = new AtomicReference<>(null);
        
        commandResolver = new CommandResolver();
        
        WikipediaService wikiService = new WikipediaService()
                .setAvaliableChars(new HashSet<>(Arrays.asList(AVAILABLE_CHARS)))
                .setMaxLength(MAX_LENGTH)
                .setMaxRetries(MAX_RETRIES)
                .setTimeout(TIMEOUT)
                .setQuerySize(QUERY_SIZE)
                .setWikiHost(WIKI_HOST);
        
        commandResolver.addCommand(new AuthCommand(activeUser, dbPath))
                .addCommand(new CreateCommand(dbPath))
                .addCommand(new ListCommand(dbPath))
                .addCommand(new HelpCommand(commandResolver::getCommands))
                .addCommand(new StartCommand(activeUser, activeHangman, wikiService))
                .addCommand(new DiscoverCommand(activeHangman, activeUser))
                .addCommand(new LogoutCommand(activeUser, activeHangman))
                .addCommand(new InfoCommand(wikiService, activeHangman));
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
                    response = ProtocolResponse.EXCEPTION.getName() + " " + exception.getMessage();
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
     * @return formatted message to log
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
