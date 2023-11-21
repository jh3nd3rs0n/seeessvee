package com.github.jh3nd3rs0n.seeessvee;

/**
 * A field of a CSV record to be written to file.
 */
public final class Field {

	/**
	 * Creates a new escaped {@code Field} from the provided {@code String}.
	 * 
	 * @param field the provided {@code String}
	 * 
	 * @return a new escaped {@code Field}
	 */
	public static Field newEscapedInstance(final String field) {
		return new Field(String.format("\"%s\"", field.replace("\"", "\"\"")));
	}
	
	/**
	 * Creates a new {@code Field} from the provided {@code String}. If the 
	 * provided {@code String} contains any reserved characters, the new 
	 * {@code Field} will be escaped.
	 * 
	 * @param field the provided {@code String}
	 * 
	 * @return a new {@code Field}
	 */
	public static Field newInstance(final String field) {
		if (field.indexOf('\"') > -1 
				|| field.indexOf(',') > -1
				|| field.indexOf('\r') > -1
				|| field.indexOf('\n') > -1) {
			return newEscapedInstance(field);
		}
		return new Field(field);
	}
	
	/** The provided {@code String} representation. */
	private final String string;
	
	/**
	 * Constructs a {@code Field} with the provided {@code String} 
	 * representation.
	 * 
	 * @param str the provided {@code String} representation
	 */
	private Field(final String str) {
		this.string = str;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Field other = (Field) obj;
		return this.string.equals(other.string);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.string.hashCode();
		return result;
	}

	/**
	 * Returns the {@code String} representation of this {@code Field}.
	 * 
	 * @return the {@code String} representation of this {@code Field}
	 */
	@Override
	public String toString() {
		return this.string;
	}
	
}
