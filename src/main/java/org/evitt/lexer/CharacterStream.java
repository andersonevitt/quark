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

import org.evitt.util.PeekableIterator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class CharacterStream implements PeekableIterator<Character> {
    private final BufferedReader reader;
    private final @NotNull Position position = new Position();


    public CharacterStream(BufferedReader reader) {
        this.reader = reader;
    }

    public CharacterStream(@NotNull InputStream stream) {
        this.reader = new BufferedReader(new InputStreamReader(stream));
    }

    public CharacterStream(@NotNull String source) {
        this.reader = new BufferedReader(new StringReader(source));
    }

    public CharacterStream(@NotNull Path input) throws FileNotFoundException {
        this(new File(input.toUri()));
    }

    public CharacterStream(@NotNull File input) throws FileNotFoundException {
        this.reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(input)));
    }

    @Override
    public boolean hasNext() {
        try {
            reader.mark(1);
            int value = reader.read();

            if (value == -1) {
                return false;
            }

            reader.reset();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public @NotNull Character next() throws NoSuchElementException {
        try {
            char value = (char) reader.read();

            if (value == '\n') {position.nextLine();} else if (value == '\r' && peek() == '\n') {position.nextLine();}

            position.nextColumn();
            return value;
        } catch (IOException e) {
            throw new NoSuchElementException(e);
        }
    }

    @Override
    public @NotNull Character peek() throws NoSuchElementException {
        try {
            reader.mark(1);
            int value = reader.read();
            reader.reset();
            return (char) value;
        } catch (IOException e) {
            throw new NoSuchElementException(e);
        }


    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public @NotNull Position getPosition() {
        return this.position;
    }
}
