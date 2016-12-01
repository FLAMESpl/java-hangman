package pl.wikihangman.views.swing.helpers;

import java.awt.Component;
import javax.swing.JOptionPane;
import pl.wikihangman.views.logging.INotification;

/**
 * Provides helping methods for {@code JOptionPane} class.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class OptionPaneHelpers {

    /**
     * Shows error dialog window with message specified by given error's code.
     * 
     * @param parentComponent sets frame that contains this message dialog,
     *      if null, default frame is set
     * @param error error code with specified message and title
     */
    public static void showErrorMessage(Component parentComponent, INotification error) {
        showNotificationMessage(parentComponent, error, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows information dialog window with message specified by given information code.
     * 
     * @param parentComponent sets frame that contains this message dialog,
     *      if null, default frame is set
     * @param info information code with specified message and title
     */
    public static void showInformationMessage(Component parentComponent, INotification info) {
        showNotificationMessage(parentComponent, info, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows dialog window with message specified by given notification.
     * 
     * @param parentComponent sets frame that contains this message dialog,
     *      if null, default frame is set
     * @param notification notification with title and message
     * @param messageType numeric code of message type
     */
    private static void showNotificationMessage(Component parentComponent, INotification notification, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, 
                notification.getMessage(), notification.getTitle(), messageType);
    }
}
