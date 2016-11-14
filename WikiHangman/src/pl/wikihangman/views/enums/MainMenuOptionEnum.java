package pl.wikihangman.views.enums;

/**
 * Available actions in main menu.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum MainMenuOptionEnum implements IActionEnum {
    EXIT("exit"),
    LOGIN("login"),
    SIGNUP("signup");
    
    private final String displayedValue;
    
    /**
     * 
     * @param displayedValue value displayed to the user
     */
    private MainMenuOptionEnum(String displayedValue) {
        this.displayedValue = displayedValue;
    }
    
    /**
     * 
     * @return displayed value of {@code MainMenuOptionEnum}
     */
    @Override
    public String getDisplayedValue() {
        return displayedValue;
    }
    
    /**
     * 
     * @return array of all displayed values of this {@code enum}
     */
    public static String[] getAllDisplayedValues() {
        return IActionEnum.fromEnumToDisplayedValues(values());
    }
}
