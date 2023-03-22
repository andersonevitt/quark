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

public interface Visitor<T> {
    T visit(BooleanExpr b);

    T visit(IntExpr i);

    T visit(FloatExpr f);

    T visit(StringExpr s);

    T visit(Symbol s);

    T visit(Sequence s);

    T visit(Lambda l);

    T visit(Call c);

    T visit(BuiltinFunction bf);

    // Hack to dynamically dispatch calls
    default T visit(Expr e) {
        if (e instanceof Symbol s) {
          return visit(s);
        } if (e instanceof BuiltinFunction bf) {
            return visit(bf);
        } else if (e instanceof Lambda l) {
            return visit(l);
        } else if (e instanceof Call c) {
            return visit(c);
        } else if (e instanceof StringExpr s) {
            return visit(s);
        } else if (e instanceof IntExpr i) {
            return visit(i);
        } else if (e instanceof FloatExpr f) {
            return visit(f);
        } else if (e instanceof BooleanExpr b) {
            return visit(b);
        } else if (e instanceof ListExpr l) {
            return visit(l);
        }

        throw new IllegalStateException("Unable to match type of expression");
    }
}
