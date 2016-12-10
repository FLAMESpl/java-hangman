package pl.wikihangman.views.swing;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import javax.swing.JPanel;

/**
 * Abstraction for panels displayed in main area of application.
 * Implements ContainerListener with dummy methods that can be overrided if
 * necessary.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AppPanel extends JPanel implements ContainerListener {

    /**
     * Part of {@code ContainerListener} interface, invoked when any component is
     * added to container. For handling an event when this component is being
     * added to container override {@code addedToPanel}.
     */
    @Override
    public void componentAdded(ContainerEvent e) {
        if (e.getComponent().equals(this)) {
            addedToPanel();
        }
    }

    /**
     * Part of {@code ContainerListener} interface, invoked when any component is
     * removed from container. For handling an event when this component is being
     * removed from the continer override {@code removedFromPanel}.
     */
    @Override
    public void componentRemoved(ContainerEvent e) {
        if (e.getChild().equals(this)) {
            removedFromPanel();
        }
    }
    
    /**
     * Invoked when this component is about to be removed from containing panel.
     * Does nothing, override if needed.
     */
    protected void removedFromPanel() {
        
    }
    
    /**
     * Invoked when this component is about to be added to container panel.
     * Does nothing, override if needed.
     */
    protected void addedToPanel() {
        
    }
}
