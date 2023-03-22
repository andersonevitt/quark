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

package org.evitt.parser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Lambda implements Expr {
    private final List<Symbol> parameters;
    private final Expr body;

    public Lambda(List<Symbol> parameters, Expr body) {
        this.parameters = parameters;
        this.body = body;
    }

    public List<Symbol> getParameters() {
        return parameters;
    }

    public Expr getBody() {
        return body;
    }

    public @NotNull String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(lambda (");
        for (Symbol param : parameters) {
            sb.append(param);
            sb.append(" ");
        }
        sb.append(") ");
        sb.append(body);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Object getValue() {
        return parameters;
    }
}