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

import org.evitt.eval.Builtin;
import org.evitt.eval.Expr;
import org.evitt.eval.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final @NotNull Map<Symbol, Expr> values;
    private final @Nullable Environment parent;

    public Environment() {
        this.values = new HashMap<>();
        this.parent = null;
        initialize();
    }

    private void initialize() {
        set("def", (Builtin) CoreLibrary::define);

        set("+", (Builtin) CoreLibrary::add);
        set("-", (Builtin) CoreLibrary::subtract);
        set("*", (Builtin) CoreLibrary::multiply);
        set("/", (Builtin) CoreLibrary::divide);
        set("^", (Builtin) CoreLibrary::power);
        set("not", (Builtin) CoreLibrary::not);
        set("and", (Builtin) CoreLibrary::and);
        set("or", (Builtin) CoreLibrary::or);

    }

    public void set(String name, Expr value) {
        set(new Symbol(name), value);
    }

    public void set(Symbol name, Expr value) {
        Environment env = this.findEnvironment(name);
        if (env != null) {
            env.values.put(name, value);
        } else {
            this.values.put(name, value);
        }
    }

    private @Nullable Environment findEnvironment(Symbol name) {
        Environment env = this;
        while (env != null && !env.values.containsKey(name)) {
            env = env.parent;
        }
        return env;
    }

    public Environment(Environment parent) {
        this.values = new HashMap<>();
        this.parent = parent;
        initialize();
    }

    public @NotNull Map<Symbol, Expr> getValues() {
        return values;
    }

    public @Nullable Environment getParent() {
        return parent;
    }

    public boolean contains(Symbol name) {
        return findEnvironment(name) != null;
    }

    public @Nullable Expr get(String name) {
        return get(new Symbol(name));
    }

    public @Nullable Expr get(Symbol name) {
        Environment env = this.findEnvironment(name);
        if (env != null && env.values.containsKey(name)) {
            return env.values.get(name);
        } else {
            return null;
        }
    }

    public String toString() {
        return values.toString();
    }
}
