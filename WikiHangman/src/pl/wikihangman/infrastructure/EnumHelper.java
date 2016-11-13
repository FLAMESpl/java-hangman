package pl.wikihangman.infrastructure;

import pl.wikihangman.views.enums.IMenuOptionEnum;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class EnumHelper {
    
    public static String[] allEnumValuesToArray(IMenuOptionEnum[] enumValues) {
        int arrayLength = enumValues.length;
        String[] result = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            result[i] = enumValues[i].getValue();
        }
        return result;
    }
}
