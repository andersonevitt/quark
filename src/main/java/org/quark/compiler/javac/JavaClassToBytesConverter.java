package org.quark.compiler.javac;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class JavaClassToBytesConverter extends SimpleJavaFileObject {

    protected ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public JavaClassToBytesConverter(String name, JavaFileObject.Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
        return bos;
    }
}
