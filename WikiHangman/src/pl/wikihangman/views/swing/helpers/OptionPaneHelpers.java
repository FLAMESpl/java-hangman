package pl.wikihangman.views.swing.helpers;

import java.awt.Component;
import javax.swing.JOptionPane;
import pl.wikihangman.client.ServerResponse;
import pl.wikihangman.views.logging.DefaultSuccessNotification;
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
     * Shows dialog window with message based on server response content.
     * If response is SUCCESS default messages are shown.
     * 
     * @param parentComponent sets frame that contains this message dialog,
     *      if null, default frame is set
     * @param response server's response
     */
    public static void showResponseMessage(Component parentComponent, ServerResponse response) {
        showResponseMessage(parentComponent, response, null);
    }
    
    /**
     * Shows dialog window with message based on server response content.
     * 
     * @param parentComponent sets frame that contains this message dialog,
     *      if null, default frame is set
     * @param response server's response
     * @param successNotification notification shown when response type is success,
     *  if null, default messages are showed
     */
    public static void showResponseMessage(Component parentComponent, 
            ServerResponse response, INotification successNotification) {
        
        if (successNotification == null) {
            successNotification = new DefaultSuccessNotification();
        }
        
        String title;
        String message;
        int messageType;
        
        switch (response.getResponseType()) {
            default:
            case EXCEPTION:
                title = "Server response error";
                message = "Communication protocol mismatch has occured. Server response is:"
                        + System.lineSeparator()
                        + response.getResponseMessage();
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
            case FAIL:
                title = "Execution denied";
                message = response.getResponseMessage();
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
            case SUCCESS:
                title = successNotification.getTitle();
                message = successNotification.getMessage();
                messageType = JOptionPane.INFORMATION_MESSAGE;
                break;
        }
        
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
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
