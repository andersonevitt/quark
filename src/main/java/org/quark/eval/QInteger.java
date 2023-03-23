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

package org.quark.eval;

import org.jetbrains.annotations.NotNull;

public record QInteger(int value) implements QNumber, QExpr {

    public @NotNull String toString() {
        return Integer.toString(value);
    }

    public @NotNull Object getValue() {
        return value;
    }

    @Override
    public QNumber plus(QNumber other) {
        return new QInteger(this.intValue() + other.intValue());
    }

    @Override
    public QNumber minus(QNumber other) {
        return new QInteger(this.intValue() - other.intValue());
    }

    @Override
    public QNumber multiply(QNumber other) {
        return new QInteger(this.intValue() * other.intValue());
    }

    @Override
    public QNumber divide(QNumber other) {
        return new QInteger(this.intValue() / other.intValue());
    }
}