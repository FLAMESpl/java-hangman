package pl.wikihangman.infrastructure;

import java.util.function.Function;

/**
 *
 * @author Łukasz Szafirski
 */
public enum YesNoEnum {
    NO("n", 0),
    YES("y", 1);
    
    private String value;
    private int code;
    
    private YesNoEnum(String value, int code) {
        this.value = value;
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getValue() {
        return value;
    }
    
    public static String[] toStringArray() {
        YesNoEnum[] values = values();
        int arrayLength = values.length;
        String[] result = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            result[i] = values[i].getValue();
        }
        return result;
    }
}
