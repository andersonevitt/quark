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

import javax.tools.*;
import java.util.Hashtable;
import java.util.Map;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final Map<String, JavaClassToBytesConverter> compiledClasses;
    private final ClassLoader loader;

    public InMemoryFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
        this.compiledClasses = new Hashtable<>();
        this.loader = new InMemoryClassLoader(this.getClass().getClassLoader(), this);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return loader;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
                                               FileObject sibling) {

        JavaClassToBytesConverter classAsBytes = new JavaClassToBytesConverter(className, kind);
        compiledClasses.put(className, classAsBytes);

        return classAsBytes;
    }

    public Map<String, JavaClassToBytesConverter> getBytesMap() {
        return compiledClasses;
    }
}