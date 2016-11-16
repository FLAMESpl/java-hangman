package pl.wikihangman;

import pl.wikihangman.infrastructure.DependencyContainer;
import pl.wikihangman.views.MasterView;


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
        MasterView masterView = new MasterView();
        masterView.start(args);
    }
    
    public static DependencyContainer registerDependencies() {
        
    }
}
