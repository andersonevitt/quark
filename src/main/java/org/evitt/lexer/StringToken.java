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

package org.evitt.lexer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StringToken implements Token {
    private final String value;

    public StringToken(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringToken that = (StringToken) o;

        return getValue() != null ? getValue().equals(that.getValue()) :
                that.getValue() == null;
    }

    @Override
    public @NotNull String toString() {
        return "STRING(\"" + value + "\")";
    }

    @Override
    public Object getValue() {
        return value;
    }
}
