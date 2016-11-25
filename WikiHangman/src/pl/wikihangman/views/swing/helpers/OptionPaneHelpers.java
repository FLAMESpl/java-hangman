package pl.wikihangman.views.swing.helpers;

import java.awt.Component;
import javax.swing.JOptionPane;
import pl.wikihangman.views.logging.ErrorsEnum;

/**
 * Provides helping methods for {@code JOptionPane} class.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class OptionPaneHelpers {

    /**
     * Shows error dialog windows with message specified by given error's code.
     * 
     * @param parentComponent sets frame that contains this message dialog,
     *      if null, default frame is set
     * @param error error code with specified message and title
     */
    public static void showErrorMessage(Component parentComponent, ErrorsEnum error) {
        JOptionPane.showMessageDialog(parentComponent, 
                error.getMessage(), error.getTitle(), JOptionPane.ERROR_MESSAGE);
    }
}
