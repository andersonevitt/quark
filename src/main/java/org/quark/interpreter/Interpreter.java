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

import org.quark.EvaluationException;
import org.quark.eval.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Interpreter implements Visitor<QExpr> {
    private Environment environment;

    public Interpreter() {
        this.environment = new Environment();
    }

    @Override
    public QExpr visit(@NotNull QBoolean b) {
        return b;
    }

    @Override
    public QExpr visit(@NotNull QInteger i) {
        return i;
    }

    @Override
    public QExpr visit(@NotNull QFloat f) {
        return f;
    }

    @Override
    public QExpr visit(@NotNull QString s) {
        return s;
    }

    @Override
    public @Nullable QExpr visit(@NotNull QSymbol s) {
        return environment.get(s);
    }

    @Override
    public QExpr visit(@NotNull QSequence s) {
        return s;
    }

    @Override
    public QExpr visit(@NotNull QLambda l) {
        return l;
    }

    @Override
    public QExpr visit(@NotNull QCall c) {
        QExpr func = visit(c.getName());
        var args = c.getArguments();

        System.out.println(c.getName().getClass());
        System.out.println(c.getArguments());
        System.out.println(func instanceof QBuiltin);

        if (func instanceof QLambda lfunc) {
            var newEnv = new Environment(environment);
            List<QSymbol> params = lfunc.parameters();

            BuiltinUtils.require(args.size() == params.size(),
                                 c.getName() + " has " + c.getArguments().size() + " arguments.");

            // Copy params
            for (int i = 0; i < params.size(); i++) {
                QSymbol param = params.get(i);
                QExpr arg = visit(args.get(i));
                newEnv.define(param, arg);
            }

            return visit(lfunc.body(), newEnv);
        } else if (func instanceof QBuiltin builtinFunc) {
            return builtinFunc.apply(this, args);
        } else {
            throw new EvaluationException("Unknown function: " + c.getName());
        }
    }


    @Override
    public QExpr visit(@NotNull QBuiltin bf) {
        return bf;
    }

    private QExpr visit(@NotNull QExpr expr, @NotNull Environment newEnv) {
        Environment oldEnv = this.environment;
        this.environment = newEnv;
        QExpr result = visit(expr);
        this.environment = oldEnv;
        return result;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
