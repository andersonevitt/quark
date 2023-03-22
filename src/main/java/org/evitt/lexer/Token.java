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

public record Token(Type type, Object value, Position position) {
    public enum Type {
        BOOLEAN,
        INTEGER,
        FLOAT,
        SYMBOL,
        STRING,
        LEFT_PAREN,
        RIGHT_PAREN,
    }

    public Token(Type type, Position position) {
        this(type, null, position);
    }

    static @NotNull Token getSymbolOrNumber(@NotNull String matched,
                                            Position p) {
        try {
            return new Token(Type.INTEGER, Integer.parseInt(matched), p);
        } catch (NumberFormatException e1) {
            try {
                return new Token(Type.FLOAT, Double.parseDouble(matched), p);
            } catch (NumberFormatException e2) {
                return new Token(Type.SYMBOL, matched, p);
            }
        }
    }

    public Object getValue() {
        return value;
    }

    public String toString() {
        if (value != null) {
            return type + "(" + value + ")";
        }

        return type.toString();
    }
}
