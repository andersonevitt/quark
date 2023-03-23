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

public non-sealed interface QNumber extends QExpr {
    QNumber plus(QNumber other);

    QNumber minus(QNumber other);

    QNumber multiply(QNumber other);

    QNumber divide(QNumber other);

    default int intValue() {
        return (int) this.getValue();
    }

    default double doubleValue() {
        return (double) this.getValue();
    }
}
