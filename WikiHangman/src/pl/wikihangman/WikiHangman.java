package pl.wikihangman;

import pl.wikihangman.core.arguments.ApplicationArguments;
import pl.wikihangman.core.arguments.InvalidArgumentsExceptions;

/**
 *
 * @author Łukasz Szafirski
 */
public class WikiHangman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ApplicationArguments arguments;
        
        try {
            arguments = new ApplicationArguments(args);
            System.out.println(arguments.getUser());
            System.out.println(arguments.getPassword());
        } catch(InvalidArgumentsExceptions invalidArguments) {
            System.out.println(invalidArguments.getMessage());
        }
        
        
    }
    
}
