package io.anqur.mbsql;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Keyword {
    private static final String NIL = "";

    public static final String SELECT = NIL;
    public static final String ALL = NIL;
    public static final String FROM = NIL;
    public static final String WHERE = NIL;
    public static final String AND = NIL;
    public static final String $ = NIL;

    @NotNull
    @Contract(pure = true)
    public static String EQ(Object lhs, Object rhs) {
        return NIL;
    }

    @NotNull
    @Contract(pure = true)
    public static String Str(String value) {
        return NIL;
    }
}
