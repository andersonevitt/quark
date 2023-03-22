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

package org.evitt.lexer;

import org.jetbrains.annotations.NotNull;

public class LexerException extends RuntimeException {
    public LexerException() {
    }

    public LexerException(String message) {
        super(message);
    }

    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexerException(Throwable cause) {
        super(cause);
    }

    public LexerException(String message, Throwable cause,
                          boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LexerException(@NotNull Position position, String message) {
        super(position.getLine() + ":" + position.getColumn() + " - " + message);
    }
}
