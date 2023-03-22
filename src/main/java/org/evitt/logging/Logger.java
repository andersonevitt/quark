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

package org.evitt.logging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Logger {
    private static Level logLevel;
    private static LoggerOutput loggerOutput;

    public static void trace(@NotNull String message, Object... args) {
        log(Level.TRACE, message, args);
    }

    public static void log(@NotNull Level currentLevel, @NotNull String message,
                    @Nullable Object... args) {
        if (logLevel.ordinal() < currentLevel.ordinal()) {
            loggerOutput.write(currentLevel, String.format(message, args));
        }
    }

    public static void debug(@NotNull String message, Object... args) {
        log(Level.DEBUG, message, args);
    }

    public static void info(@NotNull String message, Object... args) {
        log(Level.INFO, message, args);
    }

    public static void warn(@NotNull String message, Object... args) {
        log(Level.WARN, message, args);
    }

    public static void error(@NotNull String message, Object... args) {
        log(Level.ERROR, message, args);
    }

    public static void setLogLevel(Level logLevel) {
        Logger.logLevel = logLevel;
    }

    public static void setLoggerOutput(LoggerOutput loggerOutput) {
        Logger.loggerOutput = loggerOutput;
    }

    public enum Level {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
}