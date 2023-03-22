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

package org.evitt.interpreter;

import org.evitt.logging.Logger;
import org.evitt.parser.*;

public class ExprLogger implements Visitor<Void> {
    @Override
    public Void visit(BooleanExpr b) {
        Logger.debug("Visited boolean");
        return null;
    }

    @Override
    public Void visit(IntExpr i) {
        Logger.debug("Visited integer");
        return null;
    }

    @Override
    public Void visit(FloatExpr f) {
        Logger.debug("Visited float");
        return null;
    }

    @Override
    public Void visit(StringExpr s) {
        Logger.debug("Visited string");
        return null;
    }

    @Override
    public Void visit(Symbol s) {
        Logger.debug("Visited symbol");
        return null;
    }

    @Override
    public Void visit(Sequence s) {
        Logger.debug("Visited sequence");
        return null;
    }

    @Override
    public Void visit(Lambda l) {
        Logger.debug("Visited lambda");
        return null;
    }

    @Override
    public Void visit(Call c) {
        Logger.debug("Visited call");
        return null;
    }

    @Override
    public Void visit(BuiltinFunction bf) {
        Logger.debug("Visited builtin");
        return null;
    }
}
