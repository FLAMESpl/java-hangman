package pl.wikihangman.infrastructure;

import pl.wikihangman.views.enums.IMenuOptionEnum;

/**
 * Contains generic methods related to enums.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EnumHelper {
    
    /**
     * Converts array of enums instance values to array of string labels.
     * 
     * @param enumValues array of all values of specified {@code IMenuOptionEnum}
     * @return array of string labels
     */
    public static String[] allEnumValuesToArray(IMenuOptionEnum[] enumValues) {
        int arrayLength = enumValues.length;
        String[] result = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            result[i] = enumValues[i].getValue();
        }
        return result;
    }
}
