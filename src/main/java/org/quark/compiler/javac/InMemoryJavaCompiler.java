package org.quark.compiler.javac;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.Collections;
import java.util.List;

public class InMemoryJavaCompiler {
    private final String source;
    private final String name;


    public InMemoryJavaCompiler(String fullName, String source) {
        this.source = source;
        this.name = fullName;
    }

    public Class<?> compile() throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager =
                new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        List<JavaFileObject> sourceFiles = Collections.singletonList(new StringFileObject(name,
                                                                                          source));
        JavaCompiler.CompilationTask task =
                compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics().forEach(System.out::println);
            return null;
        }
        ClassLoader classLoader = manager.getClassLoader(null);
        Class<?> clazz = classLoader.loadClass(name);

        return clazz;
    }
}
