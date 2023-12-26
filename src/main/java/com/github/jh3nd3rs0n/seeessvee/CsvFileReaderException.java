package com.github.jh3nd3rs0n.seeessvee;

import java.io.IOException;

/**
 * Thrown when a {@code CsvFileReader} encounters an error when reading a CSV
 * file.
 */
public class CsvFileReaderException extends IOException {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@code CsvFileReaderException} with the provided error
     * message.
     *
     * @param message the provided error message
     */
    public CsvFileReaderException(final String message) {
        super(message);
    }

}
