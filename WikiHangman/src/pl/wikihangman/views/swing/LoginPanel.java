package pl.wikihangman.views.swing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pl.wikihangman.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.models.User;
import pl.wikihangman.services.AccountsService;
import pl.wikihangman.views.logging.ConfirmationsEnum;
import pl.wikihangman.views.logging.ErrorsEnum;
import pl.wikihangman.views.swing.events.LogInAttemptEvent;
import pl.wikihangman.views.swing.events.LogInAttemptListener;
import pl.wikihangman.views.swing.helpers.OptionPaneHelpers;

/**
 * {@code LoginPanel} displays form with textboxes and button allowing user to
 * log in. Request is handled internally and event is exposed after authentication.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class LoginPanel extends AppPanel {

    private List<LogInAttemptListener> logInAttemptListeners;
    private AccountsService accountsService = null;
            
    /**
     * Creates new form LoginPanel
     */
    public LoginPanel() {
        initComponents();
        logInAttemptListeners = new ArrayList<>();
    }
    
    /**
     * 
     * @param accountsService service that will be used to authenticate users
     * @return  this object
     */
    public LoginPanel setAccountsService(AccountsService accountsService) {
        this.accountsService = accountsService;
        return this;
    }
    
    /**
     * @param listener listener for log in attempt event
     */
    public void addLogInAttemptListener(LogInAttemptListener listener) {
        logInAttemptListeners.add(listener);
    }
    
    /**
     * Raises given event by calling each registered listener.
     * 
     * @param event model containing event-related data
     */
    public void onLogInAttempt(LogInAttemptEvent event) {
        
        logInAttemptListeners.forEach(listener -> listener.attemptedToLogIn(event));
    }
    
    /**
     * Authenticates user using given credentials. Raises LogInAttempted event
     * no matter if logging in was successful or not.
     * 
     * @param name typed user's name
     * @param password typed user's password
     */
    private void authenticate(String name, String password) {
        
        User user = null;
        try {
            user = accountsService.authenticate(name, password);
        } catch(IOException ioException) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.DB_IO);
            return;
        } catch(NumberFormatException numberFormatException) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.DB_FORMAT);
            return;
        }
        
        if (user == null) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.DB_AUTH);
        } else {
            OptionPaneHelpers.showInformationMessage(this, ConfirmationsEnum.USER_LOGGED);
        }
        
        LogInAttemptEvent event = new LogInAttemptEvent(this)
                .setSuccess(user != null)
                .setLoggedUser(user)
                .setUserName(name);
               
        onLogInAttempt(event);
    }
    
    /**
     * Creates new user in database file from given credentials.
     * Credentials cannot be empty strings.
     * 
     * @param name typed user's name
     * @param password typed user's password
     */
    private void signUp(String name, String password) {
        
        if (name.isEmpty() || password.isEmpty()) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.INPUT_EMPTY);
            return;
        }
        
        try {
            accountsService.register(name, password);
        } catch (IOException ioException) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.DB_IO);
            return;
        } catch (EntityAlreadyExistsException alreadyExistsException) {
            OptionPaneHelpers.showErrorMessage(this, alreadyExistsException);
            return;
        }
        
        OptionPaneHelpers.showInformationMessage(this, ConfirmationsEnum.USER_CREATED);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userNameLabel = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        logInButton = new javax.swing.JButton();
        passwordTextField = new javax.swing.JPasswordField();
        signUpButton = new javax.swing.JButton();

        userNameLabel.setText("User name");

        userNameTextField.setMaximumSize(new java.awt.Dimension(300, 2147483647));

        passwordLabel.setText("Password");

        logInButton.setText("Log in");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });

        passwordTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordTextField.setMaximumSize(new java.awt.Dimension(300, 2147483647));

        signUpButton.setText("Sign up");
        signUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logInButton, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(signUpButton, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(logInButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(signUpButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        
        authenticate(userNameTextField.getText(), new String(passwordTextField.getPassword()));
    }//GEN-LAST:event_logInButtonActionPerformed

    private void signUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signUpButtonActionPerformed
        signUp(userNameTextField.getText(), new String(passwordTextField.getPassword()));
    }//GEN-LAST:event_signUpButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logInButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JButton signUpButton;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JTextField userNameTextField;
    // End of variables declaration//GEN-END:variables
}
