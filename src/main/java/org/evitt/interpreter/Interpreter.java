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
import org.evitt.eval.*;

import java.util.List;

public class Interpreter implements Visitor<Expr> {
    Environment env;

    public Interpreter() {
        this.env = new Environment();
    }

    @Override
    public Expr visit(BooleanExpr b) {
        return b;
    }

    @Override
    public Expr visit(IntExpr i) {
        return i;
    }

    @Override
    public Expr visit(FloatExpr f) {
        return f;
    }

    @Override
    public Expr visit(StringExpr s) {
        return s;
    }

    @Override
    public Expr visit(Symbol s) {
        return env.get(s);
    }

    @Override
    public Expr visit(Sequence s) {
        return s;
    }

    @Override
    public Expr visit(Lambda l) {
        return l;
    }

    @Override
    public Expr visit(Call c) {

        Expr func = visit(c.getName());
        var args = c.getArguments();

        if (func instanceof Lambda lfunc) {
            var newEnv = new Environment(env);
            List<Symbol> params = lfunc.getParameters();

            BuiltinUtils.require(args.size() == params.size(),
                    c.getName() + " has " + c.getArguments().size() + " " +
                            "arguments.");

            // Copy
            for (int i = 0; i < params.size(); i++) {
                Symbol param = params.get(i);
                Expr arg = visit(args.get(i));
                newEnv.set(param, arg);
            }

            return visit(lfunc.getBody(), newEnv);
        } else if (func instanceof Builtin builtinFunc) {
            var newEnv = new Environment(env);

            return builtinFunc.apply(newEnv, args);
        } else {
            throw new EvaluationException("Unknown function: " + c.getName());
        }
    }


    @Override
    public Expr visit(Builtin bf) {
        return bf;
    }

    private Expr visit(Expr expr, Environment newEnv) {
        Environment oldEnv = this.env;
        this.env = newEnv;
        Expr result = visit(expr);
        this.env = oldEnv;
        return result;
    }
}
