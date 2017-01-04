package pl.wikihangman.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import pl.wikihangman.protocol.ProtocolParseException;

/**
 * Handles communication between tcp server and client.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class TcpClient {

    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    
    /**
     * 
     * @param ip server's ip
     * @param port server's port
     * @throws UnknownHostException
     * @throws IOException
     */
    public TcpClient(String ip, int port) throws UnknownHostException, IOException {
        
        socket = new Socket(ip, port);
        out = new PrintWriter(
                new DataOutputStream(socket.getOutputStream()), true);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
    }
    
    /**
     * Sends string to the tcp server. Text will be terminated by line separator.
     * 
     * @param command requested command
     * @param options options of requested commands
     * @throws IOException
     */
    public void send(ServerCommand command, String... options) throws IOException {
        out.println(String.format("%1$s %2$s", command, String.join(" ", options)));
    }
    
    /**
     * Receives text line from server.
     * 
     * @return text read from server
     * @throws IOException
     * @throws ProtocolParseException
     * @throws IndexOutOfBoundsException
     */
    public ServerResponse receive() throws 
            IOException, ProtocolParseException, IndexOutOfBoundsException {
        
        return new ServerResponse(in.readLine());
    }
    
    /**
     * Closes this tcp client.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        socket.close();
    }
}
