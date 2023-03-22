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

import org.evitt.interpreter.ExprLogger;
import org.evitt.interpreter.PrinterVisitor;
import org.evitt.interpreter.Interpreter;
import org.evitt.lexer.CharacterStream;
import org.evitt.lexer.Lexer;
import org.evitt.logging.Logger;
import org.evitt.logging.Logger.Level;
import org.evitt.logging.StdoutLogger;
import org.evitt.parser.Parser;

public class Main {
    public static void main(String[] args) {
        Logger.setLoggerOutput(new StdoutLogger());
        Logger.setLogLevel(Level.TRACE);
        var stream = new CharacterStream("(def x (+ 10 12 32))");
        var lexer = new Lexer(stream);
        var root = new Parser(lexer).next();
        var visitor = new Interpreter();
        var printer = new PrinterVisitor();

        System.out.println(root.accept(printer));
        root.accept(new ExprLogger());
        System.out.println(root.accept(visitor));
    }
}
