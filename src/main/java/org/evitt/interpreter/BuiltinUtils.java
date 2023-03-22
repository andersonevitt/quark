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
import org.evitt.eval.Expr;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Basic methods for working with builtin methods
 */
public class BuiltinUtils {
    private BuiltinUtils() {}

    public static void requireArity(@NotNull List<Expr> args, int arity) {
        if (args.size() != arity) {
            throw new EvaluationException("Function requires " + arity +
                                                  "arguments, only " + args.size() + " " + "supplied.");
        }
    }

    public static void requireArityAtLeast(@NotNull List<Expr> args,
                                           int arity) {
        require(args.size() <= arity,
                "Function requires at least " + arity + "arguments, only " + args.size() + " supplied.");
    }

    public static void require(boolean expr, String message) {
        if (!expr) {throw new EvaluationException(message);}
    }

    public static void requireArgType(@NotNull List<Expr> args, int arg
            , @NotNull Class<? extends Expr> clazz) {
        require(args.get(arg).getClass().isAssignableFrom(clazz),
                "Argument " + arg + " must be of type " + clazz);
    }

    public static void require(boolean expr) {
        if (!expr) {throw new EvaluationException();}
    }
}
