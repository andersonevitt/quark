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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseVisitor<T> implements Visitor<T> {
    @Override
    public @Nullable T visit(@NotNull BooleanExpr b) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull IntExpr i) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull FloatExpr f) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull StringExpr s) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull Symbol s) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull Sequence s) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull Lambda l) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull Call c) {
        return null;
    }

    @Override
    public @Nullable T visit(@NotNull Builtin bf) {
        return null;
    }
}
