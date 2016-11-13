package pl.wikihangman.views.enums;

import pl.wikihangman.infrastructure.EnumHelper;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public enum GameMenuEnum implements IMenuOptionEnum {
    EXIT("exit"),
    LOGOUT("logout"),
    SCORE("score"),
    START("start");

    private final String value;

    private GameMenuEnum(String value) {
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
