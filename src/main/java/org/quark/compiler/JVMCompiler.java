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

package org.quark.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.jetbrains.annotations.NotNull;
import org.quark.EvaluationException;
import org.quark.eval.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;

public class JVMCompiler implements Visitor<Object> {
    MethodSpec.Builder main;
    TypeSpec.Builder source;

    public JVMCompiler() {
        main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        source = TypeSpec.classBuilder("QuarkOut");
    }

    public void compile() throws EvaluationException, IOException {
        source.addMethod(main.build());
        var file = JavaFile.builder("org.quark.out", source.build()).build();
        file.writeTo(System.out);
    }

    @Override
    public Object visit(@NotNull QBoolean b) {
        return null;
    }

    @Override
    public Object visit(@NotNull QInteger i) {
        return null;
    }

    @Override
    public Object visit(@NotNull QFloat f) {
        return null;
    }

    @Override
    public Object visit(@NotNull QString s) {
        return null;
    }

    @Override
    public Object visit(@NotNull QSymbol s) {
        return null;
    }

    @Override
    public Object visit(@NotNull QSequence s) {
        return null;
    }

    @Override
    public Object visit(@NotNull QLambda l) {
        return null;
    }

    @Override
    public Object visit(@NotNull QCall c) {
        return null;
    }

    @Override
    public Object visit(@NotNull QBuiltin bf) {
        return null;
    }
}
