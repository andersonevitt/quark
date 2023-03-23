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

package org.quark;

import org.quark.lexer.*;
import org.junit.jupiter.api.Test;

import static org.quark.lexer.Token.Type;

import static org.assertj.core.api.Assertions.assertThat;


public class TestLexer {

    @Test
    public void basicTokens() {
        matches("(", new Token(Token.Type.LEFT_PAREN,new Position()));
        matches(")", new Token(Token.Type.RIGHT_PAREN, new Position()));

        matches("10", new Token(Type.INTEGER, 10, new Position()));
        matches("99",new Token(Type.INTEGER, 99, new Position()));

        matches("\"some string getValue\"", new Token(Type.STRING, "", new Position()));
    }

    private void matches(String source, Token token) {
        assertThat(create(source).next()).isEqualTo(token);
    }

    private Lexer create(String source) {
        return new Lexer(new CharacterStream(source));
    }


    @Test
    void testLeftParenToken() {
        Lexer lexer = new Lexer(new CharacterStream("("));
        Token token = lexer.next();
        assertThat(token.type()).isEqualTo(Type.LEFT_PAREN);
    }

    @Test
    void testRightParenToken() {
        Lexer lexer = new Lexer(new CharacterStream(")"));
        Token token = lexer.next();
        assertThat(token.type()).isEqualTo(Type.LEFT_PAREN);
    }

    @Test
    void testSymbolToken() {
        Lexer lexer = new Lexer(new CharacterStream("abc"));
        Token token = lexer.next();
        assertThat(token).isNotNull();
        assertThat(token.getValue()).isEqualTo("abc");
    }

    @Test
    void testIntegerToken() {
        Lexer lexer = new Lexer(new CharacterStream("123"));
        Token token = lexer.next();
        assertThat(token).isNotNull();
        assertThat(token.getValue()).isEqualTo(123);
    }

    @Test
    void testStringToken() {
        Lexer lexer = new Lexer(new CharacterStream("\"hello\""));
        Token token = lexer.next();
        assertThat(token).isNotNull();
        assertThat(token.getValue()).isEqualTo("hello");
    }

    @Test
    void testEmptyInput() {
        Lexer lexer = new Lexer(new CharacterStream(""));
        assertThat(lexer.next()).isNull();
    }

    @Test
    void testWhitespace() {
        Lexer lexer = new Lexer(new CharacterStream(" \t\n\r"));
        assertThat(lexer.next()).isNull();
    }

    @Test
    void testSingleSymbol() {
        Lexer lexer = new Lexer(new CharacterStream("a"));
        Token token = lexer.next();
        assertThat(token).isNotNull();
        assertThat(token.getValue()).isEqualTo("a");
        assertThat(lexer.next()).isNull();
    }

    @Test
    void testNegativeInteger() {
        Lexer lexer = new Lexer(new CharacterStream("-123"));
        Token token = lexer.next();
        assertThat(token).isNotNull();
        assertThat(token.getValue()).isEqualTo(-123);
        assertThat(lexer.next()).isNull();
    }

    @Test
    void testStringWithEscapedQuotes() {
        Lexer lexer = new Lexer(new CharacterStream("\"\\\"hello\\\"\""));
        Token token = lexer.next();
        assertThat(token.getValue()).isEqualTo("\"hello\"");
        assertThat(lexer.next()).isNull();
    }

}
