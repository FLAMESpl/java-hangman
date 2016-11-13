package pl.wikihangman;

import pl.wikihangman.controllers.MasterController;

/**
 * Entry point for an application.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class WikiHangman {
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MasterController master = new MasterController();
        master.runLoginScreen(args);
    }
}
