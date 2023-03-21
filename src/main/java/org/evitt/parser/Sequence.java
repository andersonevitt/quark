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

package org.evitt.parser;

public interface Sequence extends Expression {
    /**
     * Returns the first item in the sequence.
     *
     * @return the first item in the sequence
     */
    Expression getFirst();

    /**
     * Returns a sequence with the rest of the values in the sequence
     *
     * @return a sequence of all remaining values in the sequence
     */
    Sequence getRest();

    /**
     * Gets the nth getValue in the sequence
     * @param n the index of the item to return
     * @return nth item
     */
    Expression nth(int n);

    NumberExpr length();
}
