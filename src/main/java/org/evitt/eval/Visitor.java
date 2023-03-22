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

package org.evitt.eval;

import org.evitt.EvaluationException;

import java.lang.reflect.InvocationTargetException;

public interface Visitor<T> {
    T visit(BooleanExpr b);

    T visit(IntExpr i);

    T visit(FloatExpr f);

    T visit(StringExpr s);

    T visit(Symbol s);

    T visit(Sequence s);

    T visit(Lambda l);

    T visit(Call c);

    T visit(Builtin bf);

    // Horrific hack to dynamically dispatch calls where the subtype isn't
    // known at compile time
    default <E extends Expr> T visit(E e) {
        // TODO: This method could be explosive if another Expr subclass is
        //  added but isn't added to visitor

        try {
            /*
             Extremely stupid hack that finds the method with a matching
             name, invokes it with correctly cast argument types, and
             then casts the result back to the type that is wanted (unsafely)
            */
            var method = this.getClass().getMethod("visit", e.getClass());
            return (T) method.invoke(this, e);

        } catch (NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException ex) {
            throw new EvaluationException(
                    "Type error, unknown class " + e.getClass(), ex);
        }
    }
}
