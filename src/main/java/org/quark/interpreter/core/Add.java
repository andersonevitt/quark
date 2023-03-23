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

package org.quark.interpreter.core;

import org.jetbrains.annotations.NotNull;
import org.quark.EvaluationException;
import org.quark.eval.QBuiltin;
import org.quark.eval.QExpr;
import org.quark.eval.QFloat;
import org.quark.interpreter.Interpreter;

import java.util.List;

import static org.quark.interpreter.BuiltinUtils.require;

public class Add implements QBuiltin {

    @NotNull
    public QExpr apply(Interpreter visitor, @NotNull List<QExpr> args) {
        require(!args.isEmpty(), "Cannot accept zero arguments");

        if (args.size() == 1) {
            return new QFloat(+(double) args.get(0).getValue());
        }

        double start = 0;

        for (QExpr arg : args) {
            if (!(arg instanceof QFloat)) {
                throw new EvaluationException("Argument must be a number");
            }

            start += (double) arg.getValue();
        }

        return new QFloat(start);
    }
}
