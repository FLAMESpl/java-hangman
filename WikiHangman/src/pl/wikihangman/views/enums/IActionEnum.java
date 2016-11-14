package pl.wikihangman.views.enums;

/**
 * {@code Enum} containing all available action options in current context.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public interface IActionEnum {
    
    /**
     * 
     * @return displayed value of {@code enum} value
     */
    public String getDisplayedValue();
    
    /**
     * Converts IOptionValues to corresponding string display values.
     * 
     * @param optionEnumValues array of {@code enum} values
     * @return array of strings of display values
     */
    public static String[] fromEnumToDisplayedValues(IActionEnum[] optionEnumValues) {
        String[] result = new String[optionEnumValues.length];
        for (int i = 0; i < optionEnumValues.length; i++) {
            result[i] = optionEnumValues[i].getDisplayedValue();
        }
        return result;
    }
}
