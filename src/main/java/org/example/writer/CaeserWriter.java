package org.example.writer;

import java.io.IOException;
import java.io.Writer;

public class CaeserWriter extends Writer {
    private final Writer _writer;
    private final int _offset;

    public CaeserWriter(Writer writer, int offset) {
        _writer = writer;
        _offset = offset;
    }

    public char encrypt(char c) {
        if (Character.isLetter(c)) {
            char startingCharacter = Character.isLowerCase(c) ? 'a' : 'A';
            return (char) ((c - startingCharacter + _offset) % 26 + startingCharacter);
        }
        return c;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            _writer.write(encrypt(cbuf[i]));
        }
    }

    @Override
    public void flush() throws IOException {
        _writer.flush();
    }

    @Override
    public void close() throws IOException {
        _writer.close();
    }
}
