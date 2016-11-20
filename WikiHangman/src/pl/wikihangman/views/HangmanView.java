package pl.wikihangman.views;

import java.util.concurrent.atomic.AtomicBoolean;
import pl.wikihangman.exception.SessionTerminatedException;
import pl.wikihangman.models.Letter;
import pl.wikihangman.models.User;
import pl.wikihangman.views.input.*;
import pl.wikihangman.views.logging.ErrorsEnum;
import pl.wikihangman.views.models.HangmanViewModel;

/**
 * {@code HangmanView} displays data associated with solving keywords.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class HangmanView extends ViewBase {

    /**
     * String that need to be read from user input in order to exit game.
     */
    private final String exitSequence = "!q";
    private HangmanViewModel hangman = null;
    private User activeUser = null;
    
    /**
     * Copies all services from its parent.
     * 
     * @param parent calling view
     */
    public HangmanView(ViewBase parent) {
        super(parent);
    }
    
    /**
     * Runs input accepting loop for this view.
     * @param activeUser user currently logged in
     */
    public void start(User activeUser) {
        
        this.activeUser = activeUser;
        hangman = getGameService().startNewSession(activeUser.getId());
        AtomicBoolean exit = new AtomicBoolean(false);
        UserInputReader reader = new UserInputReader()
            .addQuestion("Type character to discover or " + exitSequence + " to exit");
        
        while (!exit.get()) {
            displayHangman();
            UserInputResult result = reader.read();
            processUserInput(result.get(0), exit);
        }
        
        getGameService().closeSession(activeUser.getId());
    }
    
    /**
     * Checks if given input is either exit sequence or character to discover
     * then takes appropriate action.
     * 
     * @param input user's input
     * @param exit exit token from view's loop
     */
    private void processUserInput(String input, AtomicBoolean exit) {
        
        if (input.length() > 1) {
             if (isExitSequence(input)) {
                 exit.set(areYouSureMessage());
             } else {
                 getLogger().log(ErrorsEnum.INPUT, "Single character must be provided");
             }
        } else {
            try {
                hangman = getGameService().discoverLetter(activeUser.getId(), input.charAt(0));
            } catch (SessionTerminatedException sessionTerminatedException) {
                getLogger().log(sessionTerminatedException);
            }
        }
    }
    
    /**
     * Asks user if he is sure about selected action.
     * 
     * @return option chosen by user
     */
    private boolean areYouSureMessage() {
        AtomicBoolean exit = new AtomicBoolean();
        UserActionReader reader = new UserActionReader()
                .setHeader("Are you sure?")
                .addAction("yes", () -> exit.set(true))
                .addAction("no", () -> exit.set(false));
        
        reader.read();
        return exit.get();
    }
    
    /**
     * Checks if given input is exit sequence ({@value exitSequence})
     * 
     * @param input input to be checked
     * @return true if input is exit sequence otherwise, false
     */
    private boolean isExitSequence(String input) {
        return input.equalsIgnoreCase(exitSequence);
    }
    
    private void displayHangman() {
        
        System.out.println(String.format("Lives: %1$d/%2$d",
                hangman.getActualLives(), hangman.getMaxLives()));
        for (Letter letter : hangman.getKeyword()) {
            char character = letter.isDiscovered() ? 
                    Character.toUpperCase(letter.getCharacter()) : '_';
            System.out.print(character);
        }
        System.out.println();
    }
}
