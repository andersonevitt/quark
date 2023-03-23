package org.quark.compiler.javac;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;

import static java.util.Objects.requireNonNull;

public class StringFileObject extends SimpleJavaFileObject {

    private final String sourceCode;

    public StringFileObject(String name, String sourceCode) {
        super(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
              JavaFileObject.Kind.SOURCE);
        this.sourceCode = requireNonNull(sourceCode, "sourceCode must not be null");
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}
