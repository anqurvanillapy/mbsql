# MbSQL

MbSQL (embedded SQL), a simple toy to practice writing custom Java
annotation processing.  As a Java 9+ compiler plugin, it transforms
the `@MbSql`-annotated `String`-typed variables into SQL commands in
compile-time, hence it is unable to do name-binding in runtime,
leaving `?`s as placeholders in the output.

## Motivating examples

From:

```java
import io.anqur.mbsql.MbSql;
import static io.anqur.mbsql.Keyword.*;

public class Main {
    public static void main(String[] args) {
        @MbSql String command =
            SELECT + ALL +
            FROM + "t_student" +
            WHERE + EQ("c_name", $) +
            AND + EQ("c_age", 18);
    }
}
```

Into:

```java
import io.anqur.mbsql.MbSql;
import static io.anqur.mbsql.Keyword.*;

public class Main {
    public static void main(String[] args) {
        @MbSql String command =
            "SELECT * FROM t_student WHERE c_name=? AND c_age=18;";
    }
}
```

Via:

```bash
$ javac -cp /path/to/mbsql.jar -Xplugin:MbSql Main.java
$ java -cp /path/to/mbsql.jar:. Main
```

## License

MIT
