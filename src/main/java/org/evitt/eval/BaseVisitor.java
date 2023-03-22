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

public class BaseVisitor<T> implements Visitor<T> {
    @Override
    public T visit(BooleanExpr b) {
        return null;
    }

    @Override
    public T visit(IntExpr i) {
        return null;
    }

    @Override
    public T visit(FloatExpr f) {
        return null;
    }

    @Override
    public T visit(StringExpr s) {
        return null;
    }

    @Override
    public T visit(Symbol s) {
        return null;
    }

    @Override
    public T visit(Sequence s) {
        return null;
    }

    @Override
    public T visit(Lambda l) {
        return null;
    }

    @Override
    public T visit(Call c) {
        return null;
    }

    @Override
    public T visit(Builtin bf) {
        return null;
    }
}
