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

import java.util.ArrayList;
import java.util.Collection;

public final class Vector implements Sequence {

    private final ArrayList<Expr> values;

    public Vector(Collection<Expr> values) {
        this.values = new ArrayList<>(values);
    }

    public Vector(Sequence other) {
        values = new ArrayList<>();
        for (int i = 0; i < (int) other.length().getValue(); i++) {

        }
    }

    @Override
    public Object getValue() {
        return values;
    }

    @Override
    public Expr getFirst() {
        return values.get(0);
    }

    @Override
    public Sequence getRest() {
        return null;
    }

    @Override
    public Expr nth(int n) {
        return null;
    }

    @Override
    public FloatExpr length() {
        return null;
    }
}
