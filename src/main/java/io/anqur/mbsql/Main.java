package io.anqur.mbsql;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ListTokenSource;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println((new CommandBuilder()).create(
                new ListTokenSource(List.of(
                        new CommonToken(MbSqlParser.SELECT),
                        new ExtendedToken.ID("c_score"),
                        new CommonToken(MbSqlParser.FROM),
                        new ExtendedToken.ID("t_student"),
                        new CommonToken(MbSqlParser.WHERE),
                        new ExtendedToken.EQ(
                                new ExtendedToken.ID("c_name"),
                                new ExtendedToken.StrLit("John Doe")
                        )
                ))));
    }
}
