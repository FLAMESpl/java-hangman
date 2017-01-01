package pl.wikihangman.views.swing;

import java.io.IOException;
import java.util.ArrayList;
import pl.wikihangman.client.ServerCommand;
import pl.wikihangman.client.ServerResponse;
import pl.wikihangman.client.TcpClient;
import pl.wikihangman.protocol.ProtocolCode;
import pl.wikihangman.protocol.ProtocolParseException;
import pl.wikihangman.protocol.ProtocolResponse;
import pl.wikihangman.views.logging.ErrorsEnum;
import pl.wikihangman.views.swing.helpers.OptionPaneHelpers;

/**
 * Presents and controlls hang-man session.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class GamePanel extends AppPanel {
 
    private static final char UNDISCOVERED_CHAR = '_';
    private final TcpClient client;
    private String hint = null;
    
    /**
     * Creates new form GamePanel
     * @param client tcp client that communicates with server
     */
    public GamePanel(TcpClient client) {
        initComponents();
        this.client = client;
    }
    
    /**
     * Prepares hangman session and visual components in this panel.
     * 
     * @param lives amount of lives available to solve this hangman
     * @return this object
     */
    public GamePanel startHangman(int lives) {
        
        try {
            client.send(ServerCommand.START, Integer.toString(lives));
            ServerResponse response = client.receive();
            
            if (response.getResponseType() != ProtocolResponse.SUCCESS) {
                OptionPaneHelpers.showResponseMessage(this, response);
            } else {
                initializeHangman(response);
            }
            
        } catch (IOException | ProtocolParseException exception) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.COMMUNICATION);
        }
        return this;
    }
    
    /**
     * Initializes hangman labels and text areas.
     * 
     * @param response tcp server response
     */
    private void initializeHangman(ServerResponse response) {
        labelMaxLivesAmount.setText(response.getStringToken(1));
                labelActualLivesAmount.setText(response.getStringToken(1));
                setHintFromResponse(response);

                StringBuilder keyword = new StringBuilder();
                int amountOfLetters = response.getIntToken(0);
                for (int i = 1; i < amountOfLetters; i++) {
                    keyword.append("_ ");
                }
                keyword.append("_");

                setKeywordToTextArea(keyword.toString());
    }
    
    /**
     * Creates single string hint from server response tokens.
     * 
     * @param response tcp server response containing keyword hint
     * @return keyword hint
     */
    private void setHintFromResponse(ServerResponse response) {
        StringBuilder hintBuilder = new StringBuilder();
        int i;
        for (i = 2; i < response.getLength() - 1; i++) {
            hintBuilder.append(response.getStringToken(i));
            hintBuilder.append(" ");
        }
        hintBuilder.append(response.getStringToken(i));
        hint = hintBuilder.toString();
    }
    
    /**
     * Tries to discover given letter and updates views on this panel.
     * 
     * @param character {@code char} to be discovered, case insensitive
     */
    private void tryDiscoverLetter(char character) {
        try {
            client.send(ServerCommand.DISCOVER, Character.toString(character));
            ServerResponse response = client.receive();
            if (response.getResponseType() != ProtocolResponse.SUCCESS) {
                OptionPaneHelpers.showResponseMessage(this, response);
            } else {
                formatHangmanView(response);
            }
            
        } catch (IOException | ProtocolParseException exception) {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.COMMUNICATION);
        }
    }
    
    /**
     * Updates components on this panel to current state of hangman.
     * 
     * @param response source of data for views
     * @throws ProtocolParseException
     * @throws IOException
     */
    private void formatHangmanView(ServerResponse response) 
            throws ProtocolParseException, IOException {
        
        boolean isGameOver = response.getBoolToken(0);
        labelActualLivesAmount.setText(response.getStringToken(2));
        String keyword = isGameOver ? formatArticle() : formatKeyword(response);
        setKeywordToTextArea(keyword);
    }
    
    /**
     * Constructs single string article summary about keyword from server
     * response tokens.
     * 
     * @return article summary as single string
     * @throws IOException
     * @throws ProtocolParseException
     */
    private String formatArticle() throws IOException, ProtocolParseException {
        String summary = "";
        client.send(ServerCommand.INFO);
        ServerResponse response = client.receive();
        if (response.getResponseType() != ProtocolResponse.SUCCESS) {
            OptionPaneHelpers.showResponseMessage(this, response);
        } else {
            summary = response.getResponseMessage();
        }
        return summary;
    }
    
    /**
     * Constructs single string keyword based on lettters from server response.
     * 
     * @param response server response with keyword letters
     * @return keyword as single string
     */
    private String formatKeyword(ServerResponse response) {
        
        StringBuilder keywordBuilder = new StringBuilder();
        ArrayList<Character> letters = new ArrayList<>();
        int length = response.getLength();
        for (int i = 3; i < length; i++) {
            try {
                ProtocolCode.parseBoolean(response.getStringToken(i));
                letters.add(UNDISCOVERED_CHAR);
            } catch (ProtocolParseException exception) {
                letters.add(response.getStringToken(i).charAt(0));
            }
            if (i != length) {
                letters.add(' ');
            }
        }
        
        letters.forEach(l -> keywordBuilder.append(l));
        return keywordBuilder.toString();
    }
    
    /**
     * Sets keyword to text area precceding it with hint from article category.
     * 
     * @param keyword text to be displayed in text area
     */
    private void setKeywordToTextArea(String keyword) {
        textAreaKeyword.setText(hint + System.lineSeparator());
        textAreaKeyword.append(keyword);
    }
    
    /**
     * Invoked when parent controller removes this component from panel.
     * Requests game service to terminate session.
     */
    @Override
    public void removedFromPanel() {
       // gameService.closeSession(activePlayer.getId());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelMaxLives = new javax.swing.JLabel();
        labelActualLives = new javax.swing.JLabel();
        labelMaxLivesAmount = new javax.swing.JLabel();
        labelActualLivesAmount = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaKeyword = new javax.swing.JTextArea();
        panelTextArea = new javax.swing.JPanel();
        labelInputCharacter = new javax.swing.JLabel();
        textFieldCharacterInput = new javax.swing.JTextField();
        buttonTryCharacter = new javax.swing.JButton();

        labelMaxLives.setText("Max lives:");

        labelActualLives.setText("Actual lives:");

        labelMaxLivesAmount.setText("0");

        labelActualLivesAmount.setText("0");

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        textAreaKeyword.setColumns(20);
        textAreaKeyword.setLineWrap(true);
        textAreaKeyword.setRows(5);
        jScrollPane1.setViewportView(textAreaKeyword);

        jSplitPane1.setTopComponent(jScrollPane1);

        labelInputCharacter.setText("Input character:");

        textFieldCharacterInput.setText("A");

        buttonTryCharacter.setText("Try");
        buttonTryCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTryCharacterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTextAreaLayout = new javax.swing.GroupLayout(panelTextArea);
        panelTextArea.setLayout(panelTextAreaLayout);
        panelTextAreaLayout.setHorizontalGroup(
            panelTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInputCharacter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textFieldCharacterInput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonTryCharacter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTextAreaLayout.setVerticalGroup(
            panelTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInputCharacter)
                    .addComponent(textFieldCharacterInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTryCharacter))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(panelTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelMaxLives, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelActualLives, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMaxLivesAmount)
                            .addComponent(labelActualLivesAmount))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMaxLives)
                    .addComponent(labelMaxLivesAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelActualLives)
                    .addComponent(labelActualLivesAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonTryCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTryCharacterActionPerformed
        String input = textFieldCharacterInput.getText();
        if (input.length() == 1) {
            tryDiscoverLetter(input.charAt(0));
        } else {
            OptionPaneHelpers.showErrorMessage(this, ErrorsEnum.INPUT_NEED_SINGLE);
        }
    }//GEN-LAST:event_buttonTryCharacterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonTryCharacter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel labelActualLives;
    private javax.swing.JLabel labelActualLivesAmount;
    private javax.swing.JLabel labelInputCharacter;
    private javax.swing.JLabel labelMaxLives;
    private javax.swing.JLabel labelMaxLivesAmount;
    private javax.swing.JPanel panelTextArea;
    private javax.swing.JTextArea textAreaKeyword;
    private javax.swing.JTextField textFieldCharacterInput;
    // End of variables declaration//GEN-END:variables
}
