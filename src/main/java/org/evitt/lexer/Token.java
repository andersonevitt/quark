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

public sealed interface Token permits BooleanToken, IntegerToken, LeftParenToken, RightParenToken, StringToken,
        SymbolToken {
    Token LEFT_PAREN = new LeftParenToken();
    Token RIGHT_PAREN = new RightParenToken();
    Token BOOL_TRUE = new BooleanToken(true);
    Token BOOL_FALSE = new BooleanToken(false);

    static @NotNull Token getTrue() {
        return BOOL_TRUE;
    }

    static @NotNull Token getFalse() {
        return BOOL_FALSE;
    }

    static @NotNull Token getLeftParen() {
        return LEFT_PAREN;
    }

    static @NotNull Token getRightParen() {
        return RIGHT_PAREN;
    }

    static @NotNull Token getString(String value) {
        // TODO: See if interning would actually yields better performance
        return new StringToken(value);
    }

    static @NotNull Token getSymbolOrNumber(@NotNull String matched) {
        try {
            return getInteger(Integer.parseInt(matched));
        } catch (NumberFormatException e) {
            return getSymbol(matched);
        }
    }

    static @NotNull Token getInteger(int value) {
        return new IntegerToken(value);
    }

    static @NotNull Token getSymbol(String name) {
        return new SymbolToken(name);
    }

    Object getValue();
}
