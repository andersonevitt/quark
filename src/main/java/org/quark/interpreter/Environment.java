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

package org.quark.interpreter;

import org.quark.eval.QExpr;
import org.quark.eval.QSymbol;
import org.quark.interpreter.core.Add;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final @NotNull Map<QSymbol, QExpr> values;
    private final @Nullable Environment parent;

    public Environment() {
        this.values = new HashMap<>();
        this.parent = null;
        initialize();
    }

    private void initialize() {
        define("+", new Add());
    }

    public void define(String name, QExpr value) {
        define(new QSymbol(name), value);
    }

    public void define(QSymbol name, QExpr value) {
        Environment env = this.findEnvironment(name);
        if (env != null) {
            env.values.put(name, value);
        } else {
            this.values.put(name, value);
        }
    }

    private @Nullable Environment findEnvironment(QSymbol name) {
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

    public @NotNull Map<QSymbol, QExpr> getValues() {
        return values;
    }

    public @Nullable Environment getParent() {
        return parent;
    }

    public boolean contains(QSymbol name) {
        return findEnvironment(name) != null;
    }

    public @Nullable QExpr get(String name) {
        return get(new QSymbol(name));
    }

    public @Nullable QExpr get(QSymbol name) {
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
