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

public class ListExpr implements Expression {
    private List<Expression> exprs;

    public ListExpr(List<Expression> exprs) {
        this.exprs = exprs;
    }

    public List<Expression> getValues() {
        return exprs;
    }

    public void setValues(List<Expression> exprs) {
        this.exprs = exprs;
    }

    public @NotNull String toString() {
        if (exprs.size() == 0) {
            return "()";
        }

        StringBuilder body = new StringBuilder();
        body.append("(");

        for (int i = 0; i < exprs.size() - 1; i++) {
            body.append(exprs.get(i)).append(" ");
        }

        body.append(exprs.get(exprs.size() - 1));
        body.append(")");

        return body.toString();
    }

    @Override
    public Object getValue() {
        return exprs;
    }
}
