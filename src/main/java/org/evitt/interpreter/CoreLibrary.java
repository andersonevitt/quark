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

package org.evitt.interpreter;

import org.evitt.EvaluationException;
import org.evitt.eval.BooleanExpr;
import org.evitt.eval.Expr;
import org.evitt.eval.FloatExpr;
import org.evitt.eval.Symbol;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.evitt.interpreter.BuiltinUtils.*;

public class CoreLibrary {
    private CoreLibrary() {}

    static @NotNull Expr add(Environment env,
                             @NotNull List<Expr> args) {
        require(args.isEmpty(), "Cannot accept zero arguments");

        if (args.size() == 1) {
            return new FloatExpr(+(double) args.get(0).getValue());
        }

        double start = 0;

        for (Expr arg : args) {
            if (!(arg instanceof FloatExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start += (double) arg.getValue();
        }

        return new FloatExpr(start);
    }

    static @NotNull Expr subtract(Environment env,
                                  @NotNull List<Expr> args) {
        if (args.isEmpty()) {
            throw new EvaluationException("Cannot accept zero arguments");
        } else if (args.size() == 1) {
            return new FloatExpr(-(double) args.get(0).getValue());
        }

        double start = 0;

        for (Expr arg : args) {
            if (!(arg instanceof FloatExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start -= (double) arg.getValue();
        }

        return new FloatExpr(start);
    }

    static @NotNull Expr multiply(Environment env,
                                  @NotNull List<Expr> args) {
        if (args.isEmpty() || args.size() == 1) {
            throw new EvaluationException("Must have more than 1 argument");
        }

        double start = (double) args.remove(0).getValue();

        for (Expr arg : args) {
            if (!(arg instanceof FloatExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start *= (double) arg.getValue();
        }

        return new FloatExpr(start);
    }

    static @NotNull Expr power(Environment env,
                               @NotNull List<Expr> args) {
        requireArity(args, 2);

        var base = args.get(0).getValue();
        var power = args.get(1).getValue();

        return new FloatExpr(Math.pow((double) base, (double) power));
    }

    static @NotNull Expr divide(Environment env,
                                @NotNull List<Expr> args) {
        if (args.isEmpty() || args.size() == 1) {
            throw new EvaluationException("Must have more than 1 argument");
        }

        double start = (double) args.remove(0).getValue();

        for (Expr arg : args) {
            if (!(arg instanceof FloatExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start /= (double) arg.getValue();
        }

        return new FloatExpr(start);
    }

    static @NotNull Expr not(Environment env,
                             @NotNull List<Expr> args) {
        requireArity(args, 1);
        requireArgType(args, 0, BooleanExpr.class);

        return new BooleanExpr(!(boolean) args.get(0).getValue());
    }

    static @NotNull Expr and(Environment env,
                             @NotNull List<Expr> args) {
        requireArityAtLeast(args, 2);
        requireArgType(args, 0, BooleanExpr.class);
        requireArgType(args, 1, BooleanExpr.class);

        return new BooleanExpr((boolean) args.get(0).getValue() && (boolean) args.get(1).getValue());
    }

    static @NotNull Expr or(Environment env,
                            @NotNull List<Expr> args) {
        requireArityAtLeast(args, 2);
        requireArgType(args, 0, BooleanExpr.class);
        requireArgType(args, 1, BooleanExpr.class);

        return new BooleanExpr((boolean) args.get(0).getValue() || (boolean) args.get(1).getValue());
    }

    static Expr define(@NotNull Environment env,
                       @NotNull List<Expr> args) {
        requireArity(args, 2);
        requireArgType(args, 0, Symbol.class);

        var name = ((Symbol) args.get(0));
        var value = args.get(1);

        env.getParent().set(name, value);

        return value;
    }
}
