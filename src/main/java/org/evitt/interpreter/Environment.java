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

import org.evitt.parser.BuiltinFunction;
import org.evitt.parser.Expression;
import org.evitt.parser.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final @NotNull Map<Symbol, Expression> values;
    private final @Nullable Environment parent;

    public Environment() {
        this.values = new HashMap<>();
        this.parent = null;
        initialize();
    }

    private void initialize() {
        set("def", (BuiltinFunction) CoreLibrary::define);

        set("+", (BuiltinFunction) CoreLibrary::add);
        set("-", (BuiltinFunction) CoreLibrary::subtract);
        set("*", (BuiltinFunction) CoreLibrary::multiply);
        set("/", (BuiltinFunction) CoreLibrary::divide);
        set("^", (BuiltinFunction) CoreLibrary::power);
        set("not", (BuiltinFunction) CoreLibrary::not);
        set("and", (BuiltinFunction) CoreLibrary::and);
        set("or", (BuiltinFunction) CoreLibrary::or);

    }

    public void set(String name, Expression value) {
        set(new Symbol(name), value);
    }

    public void set(Symbol name, Expression value) {
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

    public @NotNull Map<Symbol, Expression> getValues() {
        return values;
    }

    public @Nullable Environment getParent() {
        return parent;
    }

    public boolean contains(Symbol name) {
        return findEnvironment(name) != null;
    }

    public @Nullable Expression get(String name) {
        return get(new Symbol(name));
    }

    public @Nullable Expression get(Symbol name) {
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
