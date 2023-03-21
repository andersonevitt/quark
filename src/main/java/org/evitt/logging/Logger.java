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
    public enum Level {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    private Level logLevel;
    private LoggerOutput loggerOutput;

    public Logger(Level logLevel, LoggerOutput loggerOutput) {
        this.logLevel = logLevel;
        this.loggerOutput = loggerOutput;
    }

    public Logger(Level logLevel) {
        this.logLevel = logLevel;
        loggerOutput = new StdoutLogger();
    }

    public Logger() {
        this(Level.WARN);
    }

    public void log(@NotNull Level currentLevel, @NotNull String message, @Nullable Object... args) {
        if (logLevel.ordinal() < currentLevel.ordinal()) {
            loggerOutput.write(currentLevel, String.format(message, args));
        }
    }

    public void trace(@NotNull String message, Object... args) {
        log(Level.TRACE, message, args);
    }

    public void debug(@NotNull String message, Object... args) {
        log(Level.DEBUG, message, args);
    }

    public void info(@NotNull String message, Object... args) {
        log(Level.INFO, message, args);
    }

    public void warn(@NotNull String message, Object... args) {
        log(Level.WARN, message, args);
    }

    public void error(@NotNull String message, Object... args) {
        log(Level.ERROR, message, args);
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public void setLoggerOutput(LoggerOutput loggerOutput) {
        this.loggerOutput = loggerOutput;
    }
}