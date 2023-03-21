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
import org.evitt.parser.BooleanExpr;
import org.evitt.parser.Expression;
import org.evitt.parser.NumberExpr;
import org.evitt.parser.Symbol;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.evitt.interpreter.BuiltinUtils.*;

public class CoreLibrary {
    static @NotNull Expression add(Environment env, @NotNull List<Expression> args) {
        if (args.size() == 0) {
            throw new EvaluationException("Cannot accept zero arguments");
        } else if (args.size() == 1) {
            return new NumberExpr(+ (double) args.get(0).getValue());
        }

        double start = 0;

        for (Expression arg : args) {
            if (!(arg instanceof NumberExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start += (double) arg.getValue();
        }

        return new NumberExpr(start);
    }

    static @NotNull Expression subtract(Environment env, @NotNull List<Expression> args) {
        if (args.size() == 0) {
            throw new EvaluationException("Cannot accept zero arguments");
        } else if (args.size() == 1) {
            return new NumberExpr(-(double) args.get(0).getValue());
        }

        double start = 0;

        for (Expression arg : args) {
            if (!(arg instanceof NumberExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start -= (double) arg.getValue();
        }

        return new NumberExpr(start);
    }

    static @NotNull Expression multiply(Environment env, @NotNull List<Expression> args) {
        if (args.size() == 0 || args.size() == 1) {
            throw new EvaluationException("Must have more than 1 argument");
        }

        double start = (double) args.remove(0).getValue();

        for (Expression arg : args) {
            if (!(arg instanceof NumberExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start *= (double) arg.getValue();
        }

        return new NumberExpr(start);
    }

    static @NotNull Expression power(Environment env, @NotNull List<Expression> args) {
        requireArity(args, 2);

        var base = args.get(0).getValue();
        var power = args.get(1).getValue();

        return new NumberExpr(Math.pow((double) base, (double) power));
    }

    static @NotNull Expression divide(Environment env, @NotNull List<Expression> args) {
        if (args.size() == 0 || args.size() == 1) {
            throw new EvaluationException("Must have more than 1 argument");
        }

        double start = (double) args.remove(0).getValue();

        for (Expression arg : args) {
            if (!(arg instanceof NumberExpr)) {
                throw new EvaluationException("Argument must be a number");
            }

            start /= (double) arg.getValue();
        }

        return new NumberExpr(start);
    }

    static @NotNull Expression not(Environment env, @NotNull List<Expression> args) {
        requireArity(args, 1);
        requireArgType(args, 0, BooleanExpr.class);

        return new BooleanExpr(!(boolean) args.get(0).getValue());
    }

    static @NotNull Expression and(Environment env, @NotNull List<Expression> args) {
        requireArityAtLeast(args, 2);
        requireArgType(args, 0, BooleanExpr.class);
        requireArgType(args, 1, BooleanExpr.class);

        return new BooleanExpr((boolean) args.get(0).getValue() && (boolean) args.get(1).getValue());
    }

    static @NotNull Expression or(Environment env, @NotNull List<Expression> args) {
        requireArityAtLeast(args, 2);
        requireArgType(args, 0, BooleanExpr.class);
        requireArgType(args, 1, BooleanExpr.class);

        return new BooleanExpr((boolean) args.get(0).getValue() || (boolean) args.get(1).getValue());
    }

    static Expression define(@NotNull Environment env, @NotNull List<Expression> args) {
        System.out.println(args);
        requireArity(args, 2);
        requireArgType(args, 0, Symbol.class);

        var name = ((Symbol) args.get(0));
        var value = args.get(1);

        env.getParent().set(name, value);

        return value;
    }
}
