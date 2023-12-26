package com.github.jh3nd3rs0n.seeessvee;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A CSV file writer.
 */
public final class CsvFileWriter {

    /**
     * The line separator.
     */
    private final String lineSeparator;

    /**
     * The provided {@code Writer}.
     */
    private final Writer writer;

    /**
     * Creates a {@code CsvFileWriter} with the provided {@code Writer}.
     *
     * @param wrtr the provided {@code Writer}
     */
    public CsvFileWriter(final Writer wrtr) {
        this.lineSeparator = System.lineSeparator();
        this.writer = Objects.requireNonNull(wrtr);
    }

    /**
     * Writes to file the CSV record from a provided array of {@code Field}s.
     *
     * @param fields a provided array of {@code Field}s
     * @throws IOException if an I/O error occurs.
     */
    public void writeRecord(final Field... fields) throws IOException {
        this.writeRecord(Arrays.asList(fields));
    }

    /**
     * Writes to file the CSV record from a provided {@code List} of
     * {@code Field}s.
     *
     * @param fields a provided {@code List} of {@code Field}s
     * @throws IOException if an I/O error occurs.
     */
    public void writeRecord(final List<Field> fields) throws IOException {
        this.writer.write(fields.stream().map(Field::toString).collect(
                Collectors.joining(",")));
        this.writer.write(this.lineSeparator);
        this.writer.flush();
    }

    /**
     * Writes to file the CSV record from a provided {@code List} of fields as
     * {@code String}s.
     *
     * @param escapeSelection the provided {@code EscapeSelection} on the
     *                        fields
     * @param fields          a provided {@code List} of fields as
     *                        {@code String}s
     * @throws IOException if an I/O error occurs.
     */
    public void writeRecord(
            final EscapeSelection escapeSelection,
            final List<String> fields) throws IOException {
        this.writeRecord(fields.stream().map((field) -> {
            Field fld;
            switch (escapeSelection) {
                case ESCAPE_ALL:
                    fld = Field.newEscapedInstance(field);
                    break;
                case ESCAPE_REQUIRED:
                    fld = Field.newInstance(field);
                    break;
                default:
                    throw new AssertionError(String.format(
                            "unexpected escape selection: %s",
                            escapeSelection));
            }
            return fld;
        }).collect(Collectors.toList()));
    }

    /**
     * Writes to file the CSV record from a provided array of fields as
     * {@code String}s.
     *
     * @param escapeSelection the provided {@code EscapeSelection} on the
     *                        fields
     * @param fields          a provided array of fields as {@code String}s
     * @throws IOException if an I/O error occurs
     */
    public void writeRecord(
            final EscapeSelection escapeSelection,
            final String... fields) throws IOException {
        this.writeRecord(escapeSelection, Arrays.asList(fields));
    }

}
