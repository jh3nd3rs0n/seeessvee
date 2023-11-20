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
	 * Writes to file the CSV record from a provided {@code List} of fields as 
	 * {@code String}s. Fields that contain any special characters are enclosed 
	 * in double quote characters and properly escaped if necessary.
	 * 
	 * @param fields a provided {@code List} of fields as {@code String}s
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public void writeRecord(final List<String> fields) throws IOException {
		for (Iterator<String> iterator = fields.iterator(); 
				iterator.hasNext();) {
			String field = iterator.next();
			if (field.indexOf('\"') > -1) {
				field = field.replace("\"", "\"\"");
			}
			if (field.indexOf('\"') > -1 
					|| field.indexOf(',') > -1
					|| field.indexOf('\r') > -1
					|| field.indexOf('\n') > -1) {
				field = String.format("\"%s\"", field);
			}
			this.writer.write(field);
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
	 * Writes to file the CSV record from a provided array of fields as 
	 * {@code String}s. Fields that contain any special characters are enclosed 
	 * in double quote characters and properly escaped if necessary.
	 * 
	 * @param fields a provided array of fields as {@code String}s
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	public void writeRecord(final String... fields) throws IOException {
		this.writeRecord(Arrays.asList(fields));
	}

}
