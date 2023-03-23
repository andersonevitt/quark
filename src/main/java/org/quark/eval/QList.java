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

import java.util.Collections;
import java.util.LinkedList;


public final class QList implements QSequence {
    private final LinkedList<QExpr> values;

    public QList(QExpr... exprs) {
        this.values = new LinkedList<>();
        Collections.addAll(this.values, exprs);
    }

    public @NotNull String toString() {
        if (values.isEmpty()) {
            return "()";
        }

        StringBuilder body = new StringBuilder();
        body.append("(");

        for (int i = 0; i < values.size() - 1; i++) {
            body.append(values.get(i)).append(" ");
        }

        body.append(values.get(values.size() - 1));
        body.append(")");

        return body.toString();
    }

    @Override
    public Object getValue() {
        return values;
    }

    @Override
    public QExpr nth(int n) {
        return values.get(n);
    }

    @Override
    public @NotNull QInteger length() {
        return new QInteger(values.size());
    }
}
