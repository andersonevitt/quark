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

package org.quark.parser;

import org.jetbrains.annotations.NotNull;
import org.quark.eval.*;
import org.quark.lexer.Lexer;
import org.quark.lexer.Position;
import org.quark.lexer.Token;
import org.quark.util.PeekableIterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Parser implements Iterator<QExpr> {
    private final @NotNull PeekableIterator<Token> lexer;
    private final @NotNull Position position;

    public Parser(@NotNull Lexer lexer) {
        this.lexer = PeekableIterator.of(lexer);
        this.position = lexer.getPosition();
    }

    @Override
    public boolean hasNext() {
        return lexer.hasNext();
    }

    @Override
    public @NotNull QExpr next() {
        return parse();
    }

    public @NotNull QExpr parse() {
        if (!lexer.hasNext()) {
            throw new ParserException(this.position, "Unexpected EOF");
        }

        switch (lexer.peek().type()) {
            case SYMBOL -> {
                return new QSymbol((String) lexer.next().getValue());
            }
            case INTEGER -> {
                return new QInteger((int) lexer.next().getValue());
            }

            case FLOAT -> {
                return new QFloat((double) lexer.next().getValue());
            }

            case STRING -> {
                return new QString((String) lexer.next().getValue());
            }

            case BOOLEAN -> {
                return new QBoolean((Boolean) lexer.next().getValue());
            }

            case LEFT_PAREN -> {
                List<QExpr> exprs = new LinkedList<>();
                lexer.next();

                while (lexer.peek().type() != Token.Type.RIGHT_PAREN) {
                    exprs.add(parse());
                }

                lexer.next();

                var first = exprs.remove(0);
                return new QCall(first, exprs);
            }

            case RIGHT_PAREN -> {
                throw new ParserException(position, "Unexpected right parenthesis");
            }

            default -> throw new IllegalStateException("Unable to match " +
                                                               "Token: " + lexer.peek().type());
        }
    }
}
