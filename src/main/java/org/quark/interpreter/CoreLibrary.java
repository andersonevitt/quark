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
import org.quark.eval.QBoolean;
import org.quark.eval.QExpr;
import org.quark.eval.QFloat;
import org.quark.eval.QSymbol;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.quark.interpreter.BuiltinUtils.*;

public class CoreLibrary {
    private CoreLibrary() {}

    static @NotNull QExpr add(Interpreter visitor, @NotNull List<QExpr> args) {
        require(args.isEmpty(), "Cannot accept zero arguments");

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

    static @NotNull QExpr subtract(Interpreter visitor, @NotNull List<QExpr> args) {
        if (args.isEmpty()) {
            throw new EvaluationException("Cannot accept zero arguments");
        } else if (args.size() == 1) {
            return new QFloat(-(double) args.get(0).getValue());
        }

        double start = 0;

        for (QExpr arg : args) {
            if (!(arg instanceof QFloat)) {
                throw new EvaluationException("Argument must be a number");
            }

            start -= (double) arg.getValue();
        }

        return new QFloat(start);
    }

    static @NotNull QExpr multiply(Interpreter visitor, @NotNull List<QExpr> args) {
        if (args.isEmpty() || args.size() == 1) {
            throw new EvaluationException("Must have more than 1 argument");
        }

        double start = (double) args.remove(0).getValue();

        for (QExpr arg : args) {
            if (!(arg instanceof QFloat)) {
                throw new EvaluationException("Argument must be a number");
            }

            start *= (double) arg.getValue();
        }

        return new QFloat(start);
    }

    static @NotNull QExpr power(Interpreter visitor, @NotNull List<QExpr> args) {
        requireArity(args, 2);

        var base = args.get(0).getValue();
        var power = args.get(1).getValue();

        return new QFloat(Math.pow((double) base, (double) power));
    }

    static @NotNull QExpr divide(Interpreter visitor, @NotNull List<QExpr> args) {
        if (args.isEmpty() || args.size() == 1) {
            throw new EvaluationException("Must have more than 1 argument");
        }

        double start = (double) args.remove(0).getValue();

        for (QExpr arg : args) {
            if (!(arg instanceof QFloat)) {
                throw new EvaluationException("Argument must be a number");
            }

            start /= (double) arg.getValue();
        }

        return new QFloat(start);
    }

    static @NotNull QExpr not(Interpreter visitor, @NotNull List<QExpr> args) {
        requireArity(args, 1);
        requireArgType(args, 0, QBoolean.class);

        return new QBoolean(!(boolean) args.get(0).getValue());
    }

    static @NotNull QExpr and(Interpreter visitor, @NotNull List<QExpr> args) {
        requireArityAtLeast(args, 2);
        requireArgType(args, 0, QBoolean.class);
        requireArgType(args, 1, QBoolean.class);

        return new QBoolean((boolean) args.get(0).getValue() && (boolean) args.get(1).getValue());
    }

    static @NotNull QExpr or(Interpreter visitor, @NotNull List<QExpr> args) {
        requireArityAtLeast(args, 2);
        requireArgType(args, 0, QBoolean.class);
        requireArgType(args, 1, QBoolean.class);

        return new QBoolean((boolean) args.get(0).getValue() || (boolean) args.get(1).getValue());
    }

    static QExpr define(@NotNull Interpreter visitor, @NotNull List<QExpr> args) {
        requireArity(args, 2);
        requireArgType(args, 0, QSymbol.class);

        var name = args.get(0);
        var value = args.get(1);

        visitor.getEnvironment().getParent().define((QSymbol) name, visitor.visit(value));

        return value;
    }
}
