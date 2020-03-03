import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ListTokenSource;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<CommonToken> src = new ArrayList<>();
        src.add(new CommonToken(MbSqlParser.SELECT));
        src.add(new ExtendedToken.ID("c_score"));
        src.add(new CommonToken(MbSqlParser.FROM));
        src.add(new ExtendedToken.ID("t_student"));
        src.add(new CommonToken(MbSqlParser.WHERE));
        src.add(new ExtendedToken.EQ(new ExtendedToken.ID("c_name"),
                new ExtendedToken.ID("'John Doe'")));

        ListTokenSource tokenSource = new ListTokenSource(src);
        CommandBuilder builder = new CommandBuilder();
        String command = builder.create(tokenSource);
        System.out.println(command);
    }
}
