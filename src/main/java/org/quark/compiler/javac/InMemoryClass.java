package org.quark.compiler.javac;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InMemoryClass {
    Class<?> clazz;

    InMemoryClass(@NotNull String name, @NotNull String source) throws Exception {
        clazz = new InMemoryJavaCompiler(name, source).compile();
    }

    void exec() throws Exception {
        Object x = clazz.getConstructor().newInstance();

        System.out.println(Arrays.toString(clazz.getDeclaredMethods()));

        clazz.getDeclaredMethod("callSayHi", int.class).invoke(x, 13);
    }

    public Class<?> getUnderlyingClass() {
        return clazz;
    }
}
