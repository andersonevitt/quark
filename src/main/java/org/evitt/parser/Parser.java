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

import org.evitt.lexer.*;
import org.evitt.util.PeekableIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Parser implements Iterator<Expr> {
    private final @NotNull PeekableIterator<Token> lexer;
    private final Position position;

    public Parser(@NotNull Lexer lexer) {
        this.lexer = PeekableIterator.of(lexer);
        this.position = lexer.getPosition();
    }

    @Override
    public boolean hasNext() {
        return lexer.hasNext();
    }

    @Override
    public @NotNull Expr next() {
        return parse();
    }

    public @NotNull Expr parse() {
        if (!lexer.hasNext()) {
            throw new ParserException("No more tokens");
        }

        if (lexer.peek() instanceof SymbolToken) {
            return new Symbol((String) lexer.next().getValue());
        }

        if (lexer.peek() instanceof IntToken) {
            return new IntExpr((int) lexer.next().getValue());
        }

        if (lexer.peek() instanceof FloatToken) {
            return new FloatExpr((int) lexer.next().getValue());
        }

        if (lexer.peek() instanceof StringToken) {
            return new StringExpr((String) lexer.next().getValue());
        }

        if (lexer.peek() instanceof BooleanToken) {
            return new BooleanExpr((Boolean) lexer.next().getValue());
        }

        if (lexer.peek() instanceof LeftParenToken) {
            List<Expr> exprs = new LinkedList<>();
            lexer.next();

            while (!(lexer.peek() instanceof RightParenToken)) {
                exprs.add(parse());
            }

            lexer.next();

            var first = (Symbol) exprs.remove(0);
            return new Call(first, exprs);
        }


        if (lexer.peek() instanceof RightParenToken) {
            throw new ParserException(position, "Unexpected right parenthesis");
        }


        throw new IllegalStateException("Unable to match token");
    }
}
