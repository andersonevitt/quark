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

package org.quark.eval;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public record QCall(QExpr name, List<QExpr> arguments) implements QExpr {
    public QExpr getName() {
        return name;
    }

    public List<QExpr> getArguments() {
        return arguments;
    }

    public @NotNull String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(name);

        for (QExpr arg : arguments) {
            sb.append(" ");
            sb.append(arg);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Object getValue() {
        return this;
    }
}