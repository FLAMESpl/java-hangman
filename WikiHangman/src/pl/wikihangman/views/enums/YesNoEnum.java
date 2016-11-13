package pl.wikihangman.views.enums;

import pl.wikihangman.infrastructure.EnumHelper;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum YesNoEnum implements IMenuOptionEnum {
    NO("n", 0),
    YES("y", 1);
    
    private final String value;
    private final int code;
    
    private YesNoEnum(String value, int code) {
        this.value = value;
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
    public static String[] toStringArray() {
        return EnumHelper.allEnumValuesToArray(values());
    }
}
