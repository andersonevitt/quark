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

import org.quark.interpreter.Interpreter;
import org.quark.lexer.CharacterStream;
import org.quark.lexer.Lexer;
import org.quark.parser.Parser;

public class Main {
    public static void main(String[] args) {
        var stream = new CharacterStream("(+ 12.0 2.2 12.0)");
        var lexer = new Lexer(stream);
        var root = new Parser(lexer).next();
        var visitor = new Interpreter();

        System.out.println(root.accept(visitor));
    }
}
