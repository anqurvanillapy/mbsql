package io.anqur.mbsql;

import com.sun.source.tree.VariableTree;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

@Value
public class CompilerPlugin implements Plugin {
    String name = "MbSql";

    @Override
    public void init(@NotNull JavacTask javacTask, String... strings) {
        javacTask.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
            }

            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != TaskEvent.Kind.PARSE) {
                    return;
                }

                e.getCompilationUnit().accept(new TreeScanner<Void, Void>() {
                    @Override
                    public Void visitVariable(VariableTree node, Void v) {
                        boolean matched = node.getModifiers().getAnnotations()
                                .stream()
                                .anyMatch(a -> a.getClass()
                                        .getSimpleName()
                                        .equals("MbSql"));
                        Context context = ((BasicJavacTask) javacTask)
                                .getContext();
                        Log.instance(context)
                                .printRawLines(Log.WriterKind.NOTICE,
                                        "@MbSql found? " + matched);
                        return super.visitVariable(node, v);
                    }
                }, null);
            }
        });
    }
}
