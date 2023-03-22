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

import java.util.Iterator;

import static org.evitt.lexer.Token.*;

public class Lexer implements Iterator<Token> {
    private final @NotNull CharacterStream stream;
    private final @NotNull Position position;

    public Lexer(@NotNull CharacterStream stream) {
        this.stream = stream;
        this.position = stream.getPosition();
    }

    public @NotNull Position getPosition() {
        return position;
    }

    @Override
    public boolean hasNext() {
        skipWhitespace();
        return stream.hasNext();
    }

    @Override
    public @Nullable Token next() {
        skipWhitespace();

        if (!hasNext())
            return null;

        switch (stream.peek()) {
            case '(' -> {
                stream.next();
                return new Token(Type.LEFT_PAREN, position);
            }

            case ')' -> {
                stream.next();
                return new Token(Type.RIGHT_PAREN, position);
            }

            case '\"' -> {
                stream.next();
                StringBuilder matched = new StringBuilder();
                boolean escaped = false;

                while (stream.hasNext()) {
                    if (escaped) {
                        escaped = false;
                        matched.append(switch (stream.next()) {
                            case 'r' -> '\r';
                            case 'n' -> '\n';
                            case '\\' -> '\\';
                            case '\"' -> '\"';
                            default ->
                                    matched.append('\\').append(stream.peek());
                        });
                    } else if (stream.peek() == '\\') {
                        stream.next();
                        escaped = true;
                    } else if (stream.peek() == '\"') {
                        stream.next();
                        return new Token(Type.STRING, matched.toString(), position);
                    } else {
                        matched.append(stream.next());
                    }
                }

                return new Token(Type.STRING, matched.toString(), position);
            }

            case ';' -> {
                while (stream.peek() != '\n') stream.next();

                // TODO: This could potentially cause a stack overflow in
                //  really large numbers and is *slightly*
                //  inefficient
                return next();
            }

            // Symbol
            default -> {
                StringBuilder matched = new StringBuilder(8);

                while (stream.hasNext() && !Character.isWhitespace(stream.peek()) && stream.peek() != ')') {
                    matched.append(stream.next());
                }

                var strMatched = matched.toString().intern();

                if (strMatched.equals("true")) {
                    return new Token(Type.BOOLEAN, true, position);
                } else if (strMatched.equals("false")) {
                    return new Token(Type.BOOLEAN, false, position);
                }

                return Token.getSymbolOrNumber(matched.toString(), position);
            }
        }
    }

    private void skipWhitespace() {
        while (stream.hasNext() && Character.isWhitespace(stream.peek())) {
            stream.next();
        }
    }

    public @NotNull CharacterStream getStream() {
        return this.stream;
    }
}

