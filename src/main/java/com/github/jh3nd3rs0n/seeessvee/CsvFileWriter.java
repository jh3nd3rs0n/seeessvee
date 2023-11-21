package com.github.jh3nd3rs0n.seeessvee;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A CSV file writer. 
 */
public final class CsvFileWriter {

	/** The line separator. */
	private final String lineSeparator;
	
	/** The provided {@code Writer}. */
	private final Writer writer;
	
	/**
	 * Creates a {@code CsvFileWriter} with the provided {@code Writer}.
	 * 
	 * @param wrtr the provided {@code Writer}
	 */
	public CsvFileWriter(final Writer wrtr) {
		this.lineSeparator = System.getProperty("line.separator");
		this.writer = wrtr;
	}
	
	/**
	 * Writes to file the CSV record from a provided array of {@code Field}s.
	 * 
	 * @param fields a provided array of {@code Field}s
	 * 
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
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public void writeRecord(final List<Field> fields) throws IOException {
		for (Iterator<Field> iterator = fields.iterator(); 
				iterator.hasNext();) {
			Field field = iterator.next();
			this.writer.write(field.toString());
			if (iterator.hasNext()) {
				this.writer.write(",");
			} else {
				this.writer.write(this.lineSeparator);
			}
		}
		if (fields.size() > 0) {
			this.writer.flush();
		}		
	}
	
	/**
	 * Writes to file the CSV record from a provided {@code List} of fields as 
	 * {@code String}s. Fields that contain any reserved characters are enclosed 
	 * in double quote characters and properly escaped if necessary.
	 * 
	 * @param fields a provided {@code List} of fields as {@code String}s
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public void writeRecordFromStrings(
			final List<String> fields) throws IOException {
		this.writeRecord(fields.stream().map(Field::newInstance).toList());
	}
	
	/**
	 * Writes to file the CSV record from a provided array of fields as 
	 * {@code String}s. Fields that contain any reserved characters are enclosed 
	 * in double quote characters and properly escaped if necessary.
	 * 
	 * @param fields a provided array of fields as {@code String}s
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	public void writeRecordFromStrings(
			final String... fields) throws IOException {
		this.writeRecordFromStrings(Arrays.asList(fields));
	}
 	
	/**
	 * Writes to file the CSV record from a provided {@code List} of fields as 
	 * {@code String}s to be escaped. Fields that contain any double quote 
	 * characters are properly escaped.
	 * 
	 * @param fields a provided {@code List} of fields as {@code String}s to be 
	 * escaped
	 * 
	 * @throws IOException if an I/O error occurs.
	 */	
	public void writeRecordFromStringsToBeEscaped(
			final List<String> fields) throws IOException {
		this.writeRecord(fields.stream().map(Field::newEscapedInstance).toList());
	}
	
	/**
	 * Writes to file the CSV record from a provided array of fields as 
	 * {@code String}s to be escaped. Fields that contain any double quote 
	 * characters are properly escaped.
	 * 
	 * @param fields a provided array of fields as {@code String}s to be 
	 * escaped
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public void writeRecordFromStringsToBeEscaped(
			final String... fields) throws IOException {
		this.writeRecordFromStringsToBeEscaped(Arrays.asList(fields));
	}

}
