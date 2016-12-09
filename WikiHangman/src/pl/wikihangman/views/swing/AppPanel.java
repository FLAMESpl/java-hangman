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
     * Part of {@code ContainerListener} interface, invoked when component is
     * added to container. Does nothing, override if
     * necessary.
     */
    @Override
    public void componentAdded(ContainerEvent e) {
    }

    /**
     * Part of {@code ContainerListener} interface, invoked when component is
     * removed from container. Does nothing, override if
     * necessary.
     */
    @Override
    public void componentRemoved(ContainerEvent e) {
    }
}
