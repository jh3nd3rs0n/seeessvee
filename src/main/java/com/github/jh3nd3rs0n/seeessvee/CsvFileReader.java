package com.github.jh3nd3rs0n.seeessvee;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A CSV file reader.
 */
public final class CsvFileReader {

    /**
     * The provided {@code Reader}.
     */
    private final Reader reader;

    /**
     * The current character from the provided {@code Reader}.
     */
    private int chr;

    /**
     * The ending index of the escaped text.
     */
    private int escapedTextEndIndex;

    /**
     * The starting index of the escaped text.
     */
    private int escapedTextStartIndex;

    /**
     * A field of the CSV record.
     */
    private String field;

    /**
     * The current index of a field.
     */
    private int index;

    /**
     * The boolean value to indicate if the current index of a field is at the
     * end of the escaped text.
     */
    private boolean isAtEndOfEscapedText;

    /**
     * The boolean value to indicate if the current index of a field is
     * immediately after the end of the escaped text.
     */
    private boolean isImmediatelyAfterEscapedText;

    /**
     * The boolean value to indicate if the current index of a field is within
     * escaped text.
     */
    private boolean isWithinEscapedText;

    /**
     * The boolean value to indicate if the current index of a field is within
     * non-escaped text.
     */
    private boolean isWithinNonescapedText;

    /**
     * The boolean value to indicate if the current index of a field is within
     * non-escaped text or is immediately after the end of the escaped text.
     */
    private boolean isWithinNonescapedTextOrImmediatelyAfterEscapedText;

    /**
     * The line separator.
     */
    private String lineSeparator;

    /**
     * The {@code StringBuilder}.
     */
    private StringBuilder stringBuilder;

    /**
     * Constructs a {@code CsvFileReader} with the provided {@code Reader}.
     *
     * @param rdr the provided {@code Reader}
     */
    public CsvFileReader(final Reader rdr) {
        this.reader = Objects.requireNonNull(rdr);
        this.initialize();
    }

    /**
     * Creates the {@code StringBuilder} if it is not created.
     */
    private void createStringBuilderIfNotCreated() {
        if (this.stringBuilder == null) {
            this.stringBuilder = new StringBuilder();
        }
    }

    /**
     * Initializes this {@code CsvFileReader}'s instance variables.
     */
    private void initialize() {
        this.chr = -1;
        this.escapedTextEndIndex = -1;
        this.escapedTextStartIndex = -1;
        this.field = null;
        this.index = -1;
        this.isAtEndOfEscapedText = false;
        this.isImmediatelyAfterEscapedText = false;
        this.isWithinEscapedText = false;
        this.isWithinNonescapedText = false;
        this.isWithinNonescapedTextOrImmediatelyAfterEscapedText = false;
        this.lineSeparator = null;
        this.stringBuilder = null;
    }

    /**
     * Performs the proper action when the current character is not any of the
     * following: non-escaped comma character, non-escaped carriage return
     * character, non-escaped line feed character, double quote character.
     *
     * @throws CsvFileReaderException if the current character is immediately
     *                                after escaped text
     */
    private void onAnyOtherCharacter() throws CsvFileReaderException {
        if (this.isImmediatelyAfterEscapedText) {
            throw new CsvFileReaderException(
                    "field containing a double quote character ('\"') "
                            + "must be escaped in double quotes and the "
                            + "double quote character inside the field must "
                            + "be escaped by preceding it with another double "
                            + "quote character");
        }
        char ch = (char) this.chr;
        this.stringBuilder.append(ch);
    }

    /**
     * Performs the proper action when the current character is a double quote
     * character.
     *
     * @throws CsvFileReaderException if the double quote character is the
     *                                first double quote character to appear
     *                                after the start of the field
     */
    private void onDoubleQuoteCharacter() throws CsvFileReaderException {
        if (this.isWithinNonescapedText && this.index > 0) {
            throw new CsvFileReaderException(
                    "field containing a double quote character ('\"') "
                            + "must be escaped in double quotes and the "
                            + "double quote character inside the field must "
                            + "be escaped by preceding it with another double "
                            + "quote character");
        } else if (this.index == 0) {
            this.escapedTextStartIndex = this.index;
            this.updateConditions();
        } else if (this.isWithinEscapedText) {
            this.escapedTextEndIndex = this.index;
            this.updateConditions();
        } else if (this.isImmediatelyAfterEscapedText) {
            this.stringBuilder.replace(
                    this.escapedTextEndIndex, this.index, "");
            this.escapedTextEndIndex = -1;
            this.index--;
            this.updateConditions();
        }
    }

    /**
     * Performs the proper action when the end of the provided {@code Reader}
     * has been reached.
     *
     * @throws CsvFileReaderException if a closing double quote character has
     *                                not been found
     */
    private void onEndOfReader() throws CsvFileReaderException {
        if (this.isWithinEscapedText) {
            throw new CsvFileReaderException(
                    "missing closing double quote character ('\"')");
        }
        this.lineSeparator = null;
        if (this.stringBuilder == null) {
            this.field = null;
        } else {
            if (this.isAtEndOfEscapedText) {
                String substring = this.stringBuilder.substring(
                        this.escapedTextStartIndex + 1,
                        this.escapedTextEndIndex);
                this.stringBuilder.replace(
                        this.escapedTextStartIndex,
                        this.escapedTextEndIndex + 1,
                        substring);
            }
            this.field = this.stringBuilder.toString();
            this.stringBuilder.delete(0, this.stringBuilder.length());
            this.stringBuilder = null;
        }
        this.escapedTextStartIndex = -1;
        this.escapedTextEndIndex = -1;
        this.index = -1;
        this.updateConditions();
    }

    /**
     * Performs the proper action when the current character is a non-escaped
     * carriage return character. A {@code CsvFileReaderException} is thrown
     * if the current character is not escaped in double quotes or is not
     * followed by a line feed character.
     *
     * @throws IOException if an I/O error occurs
     */
    private void onNonescapedCarriageReturnCharacter() throws IOException {
        char ch = (char) this.chr;
        if ((this.chr = this.reader.read()) != -1) {
            char c = (char) this.chr;
            if (c == '\n') {
                this.lineSeparator = new String(new char[]{ch, c});
                this.removeEnclosingDoubleQuoteCharacters();
                this.field = this.stringBuilder.toString();
                this.stringBuilder.delete(0, this.stringBuilder.length());
                this.stringBuilder = null;
                return;
            }
        }
        throw new CsvFileReaderException(
                "field containing carriage return character ('\\r') "
                        + "must be escaped in double quotes or the carriage "
                        + "return character must be followed by a line feed "
                        + "character ('\\n')");
    }

    /**
     * Performs the proper action when the current character is a non-escaped
     * comma character.
     */
    private void onNonescapedCommaCharacter() {
        this.lineSeparator = null;
        this.removeEnclosingDoubleQuoteCharacters();
        this.field = this.stringBuilder.toString();
        this.stringBuilder.delete(0, this.stringBuilder.length());
    }

    /**
     * Performs the proper action when the current character is a non-escaped
     * line feed character.
     */
    private void onNonescapedLineFeedCharacter() {
        char ch = (char) this.chr;
        this.lineSeparator = String.valueOf(ch);
        this.removeEnclosingDoubleQuoteCharacters();
        this.field = this.stringBuilder.toString();
        this.stringBuilder.delete(0, this.stringBuilder.length());
        this.stringBuilder = null;
    }

    /**
     * Reads a field from a CSV record.
     *
     * @throws IOException if an I/O error occurs
     */
    private void readField() throws IOException {
        this.initialize();
        while ((this.chr = this.reader.read()) != -1) {
            this.createStringBuilderIfNotCreated();
            this.index++;
            this.updateConditions();
            char ch = (char) this.chr;
            if (ch == ',' &&
                    this.isWithinNonescapedTextOrImmediatelyAfterEscapedText) {
                this.onNonescapedCommaCharacter();
                return;
            } else if (ch == '\r' &&
                    this.isWithinNonescapedTextOrImmediatelyAfterEscapedText) {
                this.onNonescapedCarriageReturnCharacter();
                return;
            } else if (ch == '\n' &&
                    this.isWithinNonescapedTextOrImmediatelyAfterEscapedText) {
                this.onNonescapedLineFeedCharacter();
                return;
            } else {
                if (ch == '\"') {
                    this.onDoubleQuoteCharacter();
                }
                this.onAnyOtherCharacter();
            }
        }
        this.onEndOfReader();
    }

    /**
     * Reads a CSV record as a {@code List} of fields as {@code String}s
     * stripped of enclosing double quote characters and double quote
     * characters escaping other double quote characters. An empty {@code List}
     * is returned if there are no more CSV records.
     *
     * @return a CSV record as a {@code List} of fields as {@code String}s or
     * an empty {@code List} if there are no more CSV records
     * @throws IOException if an I/O error occurs
     */
    public List<String> readRecord() throws IOException {
        List<String> fields = new ArrayList<>();
        do {
            this.readField();
            if (this.field != null) {
                fields.add(this.field);
            }
        } while (this.field != null && this.lineSeparator == null);
        return Collections.unmodifiableList(fields);
    }

    /**
     * Removes the enclosing double quote characters of a field.
     */
    private void removeEnclosingDoubleQuoteCharacters() {
        if (this.isImmediatelyAfterEscapedText) {
            String substring = this.stringBuilder.substring(
                    this.escapedTextStartIndex + 1, this.escapedTextEndIndex);
            this.stringBuilder.replace(
                    this.escapedTextStartIndex,
                    this.escapedTextEndIndex + 1,
                    substring);
            this.escapedTextStartIndex = -1;
            this.escapedTextEndIndex = -1;
            this.index -= 2;
            this.updateConditions();
        }
    }

    /**
     * Updates the conditions after an action was performed.
     */
    private void updateConditions() {
        this.isAtEndOfEscapedText = this.escapedTextStartIndex > -1
                && this.escapedTextEndIndex > -1
                && this.escapedTextEndIndex == this.index;
        this.isImmediatelyAfterEscapedText = this.escapedTextStartIndex > -1
                && this.escapedTextEndIndex > -1
                && this.escapedTextEndIndex == this.index - 1;
        this.isWithinEscapedText = this.escapedTextStartIndex > -1
                && this.escapedTextEndIndex == -1;
        this.isWithinNonescapedText = this.escapedTextStartIndex == -1
                && this.escapedTextEndIndex == -1;
        this.isWithinNonescapedTextOrImmediatelyAfterEscapedText =
                this.isWithinNonescapedText
                        || this.isImmediatelyAfterEscapedText;
    }

}
