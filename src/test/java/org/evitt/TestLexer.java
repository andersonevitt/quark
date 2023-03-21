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

package org.evitt;

import org.evitt.lexer.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TestLexer {

    @Test
    public void basicTokens() {
        matches("(", new LeftParenToken());
        matches(")", new RightParenToken());

        matches("10", new IntegerToken(10));
        matches("99", new IntegerToken(99));

        matches("\"some string getValue\"", new StringToken("some string getValue"));
    }

    private void matches(String source, Token token) {
        assertThat(create(source).next()).isEqualTo(token);
    }

    private Lexer create(String source) {
        return new Lexer(new CharacterStream(source));
    }

    @Test
    public void lists() {
        matches("(some or)", List.of(
                new LeftParenToken(), new SymbolToken("some"), new SymbolToken("or"), new RightParenToken()));
        matches("(())", List.of(
                new LeftParenToken(), new LeftParenToken(), new RightParenToken(), new RightParenToken()));

        matches("(() some)", List.of(
                new LeftParenToken(), new LeftParenToken(), new RightParenToken(), new SymbolToken("some"),
                new RightParenToken()));
    }

    private void matches(String source, List<Token> tokens) {
        List<Token> sourceTokens = new LinkedList<>();
        create(source).forEachRemaining(sourceTokens::add);

        assertThat(tokens).isEqualTo(sourceTokens);
    }

    @Test
    public void whitespace() {
        matches("  \n \n \n ( \n \n \n      some)\n\n\n", List.of(new LeftParenToken(), new SymbolToken("some"),
                new RightParenToken()));
        matches("(some\nother)", List.of(new LeftParenToken(), new SymbolToken("some"), new SymbolToken("other"),
                new RightParenToken()));
    }

    @Test
    void testLeftParenToken() {
        Lexer lexer = new Lexer(new CharacterStream("("));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(LeftParenToken.class);
    }

    @Test
    void testRightParenToken() {
        Lexer lexer = new Lexer(new CharacterStream(")"));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(RightParenToken.class);
    }

    @Test
    void testSymbolToken() {
        Lexer lexer = new Lexer(new CharacterStream("abc"));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(SymbolToken.class);
        assertThat(token.getValue()).isEqualTo("abc");
    }

    @Test
    void testIntegerToken() {
        Lexer lexer = new Lexer(new CharacterStream("123"));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(IntegerToken.class);
        assertThat(((IntegerToken) token).getValue()).isEqualTo(123);
    }

    @Test
    void testStringToken() {
        Lexer lexer = new Lexer(new CharacterStream("\"hello\""));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(StringToken.class);
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
        assertThat(token).isInstanceOf(SymbolToken.class);
        assertThat(token.getValue()).isEqualTo("a");
        assertThat(lexer.next()).isNull();
    }

    @Test
    void testNegativeInteger() {
        Lexer lexer = new Lexer(new CharacterStream("-123"));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(IntegerToken.class);
        assertThat(((IntegerToken) token).getValue()).isEqualTo(-123);
        assertThat(lexer.next()).isNull();
    }

    @Test
    void testStringWithEscapedQuotes() {
        Lexer lexer = new Lexer(new CharacterStream("\"\\\"hello\\\"\""));
        Token token = lexer.next();
        assertThat(token).isInstanceOf(StringToken.class);
        assertThat(token.getValue()).isEqualTo("\"hello\"");
        assertThat(lexer.next()).isNull();
    }

}
