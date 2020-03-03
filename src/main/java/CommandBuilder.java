import lombok.EqualsAndHashCode;
import lombok.Value;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

class ExtendedToken {
    @EqualsAndHashCode(callSuper = true)
    @Value
    static
    class ID extends CommonToken {
        String value;

        public ID(String value) {
            super(MbSqlParser.ID);
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    static
    class EQ extends CommonToken {
        CommonToken lhs;
        CommonToken rhs;

        public EQ(CommonToken lhs, CommonToken rhs) {
            super(MbSqlParser.EQ);
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public String toString() {
            return lhs.toString() + " = " + rhs.toString();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    static class StrLit extends CommonToken {
        String value;

        public StrLit(String value) {
            super(MbSqlParser.STRING_LITERAL);
            this.value = value;
        }

        @Override
        public String toString() {
            return "\"" + value + "\"";
        }
    }
}

class CommandBuilder extends MbSqlBaseListener {
    private final StringBuilder commandText = new StringBuilder();

    String create(TokenSource tokenSource) {
        TokenStream stream = new CommonTokenStream(tokenSource);
        MbSqlParser parser = new MbSqlParser(stream);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, parser.query());
        return commandText.toString();
    }

    @Override
    public void enterSelectStmt(@NotNull MbSqlParser.SelectStmtContext ctx) {
        commandText.append(" select ");

        TerminalNode colName = ctx.ID(0);
        if (colName != null) {
            commandText.append(colName.getSymbol());
        } else {
            commandText.append('*');
        }

        commandText.append(" from ");

        TerminalNode tableName = ctx.ID(1);
        commandText.append(tableName.getSymbol());

        TerminalNode where = ctx.WHERE();
        if (where != null) {
            commandText.append(" where ");
            commandText.append(ctx.EQ().getSymbol());
        }

        commandText.append(';');
    }
}
