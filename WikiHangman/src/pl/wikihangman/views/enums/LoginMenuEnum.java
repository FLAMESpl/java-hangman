package pl.wikihangman.views.enums;

import pl.wikihangman.infrastructure.EnumHelper;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum LoginMenuEnum implements IMenuOptionEnum {
    
    EXIT("exit"),
    LOGIN("login"),
    SIGNUP("signup");

    private final String value;

    private LoginMenuEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static String[] toStringArray() {
        return EnumHelper.allEnumValuesToArray(values());
    }
} 