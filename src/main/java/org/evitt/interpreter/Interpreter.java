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
import org.evitt.eval.Evaluator;
import org.evitt.parser.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements Evaluator {
    private Environment environment;

    public Interpreter() {
        environment = new Environment();
    }

    public @Nullable Expression eval(Expression expr) {
        if (expr instanceof NumberExpr) {
            return expr;
        } else if (expr instanceof Symbol sexpr) {
            return environment.get(sexpr);
        } else if (expr instanceof Lambda) {
            return expr;
        } else if (expr instanceof BooleanExpr) {
            return expr;
        } else if (expr instanceof BuiltinFunction bf) {
            return bf;
        } else if (expr instanceof Call call) {
            Expression func = eval(call.getName());

            var args = call.getArguments();
            if (func instanceof Lambda lfunc) {
                var newEnv = new Environment(environment);
                List<Symbol> params = lfunc.getParameters();

                if (args.size() != params.size())
                    throw new EvaluationException(call.getName() + " has " + call.getArguments().size() + " arguments" +
                            ".");

                for (int i = 0; i < params.size(); i++) {
                    Symbol param = params.get(i);
                    Expression arg = eval(args.get(i));
                    newEnv.set(param, arg);
                }

                return eval(lfunc.getBody(), newEnv);
            } else if (func instanceof BuiltinFunction builtinFunc) {
                var newEnv = new Environment(environment);
                return builtinFunc.apply(newEnv, args);


            } else {
                throw new RuntimeException("Unknown function: " + call.getName());
            }
        }

        throw new IllegalStateException("Unknown expression: " + expr);
    }

    /**
     * Creates a new sub-environment by creating a new environment
     * and copying all the arguments to the new environment
     *
     * @param params parameters that should be copied
     * @param args
     * @return the new environment with all arguments now added
     */
    private @NotNull Environment newEnvironment(@NotNull List<Symbol> params, @NotNull List<Expression> args) {
        var newEnv = new Environment(environment);

        if (args.size() != params.size()) throw new EvaluationException("Invalid number of arguments");

        for (int i = 0; i < args.size(); i++) {
            Symbol param = params.get(i);
            Expression arg = eval(args.get(i));
            newEnv.set(param, arg);
        }

        return newEnv;
    }

    private Expression eval(Expression expr, Environment env) {
        Environment oldEnv = this.environment;
        this.environment = env;
        Expression result = eval(expr);
        this.environment = oldEnv;
        return result;
    }

    private @NotNull List<Expression> evalList(@NotNull List<Expression> args) {
        List<Expression> evaluatedArgs = new ArrayList<>();
        for (Expression arg : args) {
            evaluatedArgs.add(eval(arg));
        }
        return evaluatedArgs;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
