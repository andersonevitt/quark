/*
 * Copyright 2023 Anderson Evitt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
