package io.anqur.mbsql;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import lombok.Getter;
import org.antlr.v4.runtime.CommonToken;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CompilerPlugin implements Plugin {
    @Getter
    private String name = "MbSql";

    private static final String errorPrefix = "(@MbSql) Error: ";

    private static void error(Context ctx, String msg) {
        Log.instance(ctx).printRawLines(Log.WriterKind.ERROR,
                errorPrefix + msg);
    }

    @Override
    public void init(@NotNull JavacTask javacTask, String... args) {
        Context ctx = ((BasicJavacTask) javacTask)
                .getContext();

        javacTask.addTaskListener(new TaskListener() {
            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != TaskEvent.Kind.PARSE) {
                    return;
                }

                e.getCompilationUnit().accept(new TreeScanner<Void, Void>() {
                    @Override
                    public Void visitVariable(VariableTree node, Void v) {

                        boolean matched = node
                                .getModifiers()
                                .getAnnotations()
                                .stream()
                                .anyMatch(a -> a.toString().equals("@MbSql"));

                        if (matched) {
                            transformCommand(node, ctx);
                        }

                        return super.visitVariable(node, v);
                    }
                }, null);
            }
        });
    }

    private static void transformCommand(@NotNull VariableTree node, Context ctx) {
        if (!node.getType().toString().equals("String")) {
            error(ctx, "Skipping bad variable type '" + node.getType() +
                    "', expected 'String'");
            return;
        }

        ExpressionTree initializer = node.getInitializer();
        if (initializer == null) {
            return;
        }

        List<CommonToken> tokens = new ArrayList<>();

        initializer.accept(new TreeScanner<Void, Void>() {
            @Override
            public Void visitLiteral(LiteralTree node, Void v) {
                Log.instance(ctx).printRawLines(Log.WriterKind.NOTICE,
                        node.toString());
                return super.visitLiteral(node, v);
            }

            @Override
            public Void visitIdentifier(IdentifierTree node, Void v) {
                Log.instance(ctx).printRawLines(Log.WriterKind.NOTICE,
                        node.toString());
                return super.visitIdentifier(node, v);
            }
        }, null);
    }
}
