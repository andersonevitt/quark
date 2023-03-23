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

package org.quark.interpreter;

import org.jetbrains.annotations.NotNull;
import org.quark.eval.*;

public class ExprPrinter implements Visitor<String> {

    @Override
    public @NotNull String visit(@NotNull QBoolean b) {
        return b.toString();
    }

    @Override
    public @NotNull String visit(@NotNull QInteger i) {
        return i.toString();
    }

    @Override
    public @NotNull String visit(@NotNull QFloat f) {
        return f.toString();
    }

    @Override
    public @NotNull String visit(@NotNull QString s) {
        return s.toString();
    }

    @Override
    public String visit(@NotNull QSymbol s) {
        return s.toString();
    }

    @Override
    public String visit(@NotNull QSequence s) {
        return s.toString();
    }

    @Override
    public @NotNull String visit(@NotNull QLambda l) {
        return l.toString();
    }

    @Override
    public @NotNull String visit(@NotNull QCall c) {
        StringBuilder start = new StringBuilder();
        start.append('(').append(c.getName()).append(' ');

        if (!c.getArguments().isEmpty()) {
            for (int i = 0; i < c.getArguments().size() - 1; i++) {
                start.append(c.getArguments().get(i)).append(" ");
            }
            start.append(c.getArguments().get(c.getArguments().size() - 1));
        }

        start.append(')');

        return start.toString();
    }

    @Override
    public String visit(@NotNull QBuiltin bf) {
        return bf.toString();
    }
}
