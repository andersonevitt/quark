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

package org.quark.lexer;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Position implements Comparable<Position> {
    private int line;
    private int column;

    public Position() {
        line = 1;
        column = 1;
    }

    public Position(@NotNull Position other) {
        this(other.getLine(), other.getLine());
    }

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void nextLine() {
        line += 1;
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        return result;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        if (line != position.line) {return false;}
        return column == position.column;
    }

    @Override
    public String toString() {
        return getLine() + ":" + getColumn();
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void nextColumn() {
        column += 1;
    }

    @Override
    public int compareTo(@NotNull Position o) {
        if (o.getColumn() < this.getColumn()) {
            return -1;
        } else if (o.getColumn() == this.column) {
            if (o.getLine() < this.getLine()) {
                return -1;
            } else if (o.getLine() == this.getLine()) {
                return 0;
            }
            return 1;
        }
        return 1;
    }
}