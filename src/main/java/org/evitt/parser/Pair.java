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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pair implements Expression {
    // car
    private @NotNull Expression first;
    // cdr
    private @Nullable Expression rest;

    public Pair(@NotNull Expression first, @Nullable Expression rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public @NotNull String toString() {
        return "Pair(" + first + ", " + rest + ')';
    }

    public @NotNull Expression getFirst() {
        return first;
    }

    public void setFirst(@NotNull Expression first) {
        this.first = first;
    }

    public @Nullable Expression getRest() {
        return rest;
    }

    public void setRest(@Nullable Expression rest) {
        this.rest = rest;
    }

    @Override
    public Object getValue() {
        return first.getValue();
    }
}
