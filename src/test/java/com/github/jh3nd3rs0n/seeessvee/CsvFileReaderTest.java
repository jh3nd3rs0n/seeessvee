package com.github.jh3nd3rs0n.seeessvee;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CsvFileReaderTest {

	@Test
	public void test() throws IOException {
		StringReader stringReader = new StringReader(
				"aaa,bbb,ccc\r\n"
				+ "zzz,yyy,xxx\r\n");
		List<List<String>> expected = Arrays.asList(
				Arrays.asList("aaa", "bbb", "ccc"),
				Arrays.asList("zzz", "yyy", "xxx"));
		List<List<String>> actual = new ArrayList<List<String>>();
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		List<String> csvRecord = null;
		while ((csvRecord = csvFileReader.readRecord()).size() > 0) {
			actual.add(csvRecord);
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWithDifferentLineEndings() throws IOException {
		StringReader stringReader = new StringReader(
				"aaa,bbb,ccc\n"
				+ "zzz,yyy,xxx\r\n");
		List<List<String>> expected = Arrays.asList(
				Arrays.asList("aaa", "bbb", "ccc"),
				Arrays.asList("zzz", "yyy", "xxx"));
		List<List<String>> actual = new ArrayList<List<String>>();
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		List<String> csvRecord = null;
		while ((csvRecord = csvFileReader.readRecord()).size() > 0) {
			actual.add(csvRecord);
		}
		assertEquals(expected, actual);		
	}
	
	@Test(expected = CsvFileReaderException.class)
	public void testWithEscapedAndNonEscapedField() throws IOException {
		StringReader stringReader = new StringReader(
				"aaa,\"b\"bb,ccc\r\n");
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		csvFileReader.readRecord();
	}
	
	@Test(expected = CsvFileReaderException.class)
	public void testWithFieldContainingNonescapedCarriageReturnCharacter() throws IOException {
		StringReader stringReader = new StringReader(
				"aaa,bb\rb,ccc\r\n");
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		csvFileReader.readRecord();
	}
	
	@Test(expected = CsvFileReaderException.class)
	public void testWithFieldContainingNonescapedDoubleQuoteCharacter() throws IOException {
		StringReader stringReader = new StringReader(
				"aaa,bb\"b,ccc\r\n");
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		csvFileReader.readRecord();
	}
	
	@Test
	public void testWithFieldEnclosedWithDoubleQuotesContainingEscapedDoubleQuote() throws IOException {
		StringReader stringReader = new StringReader(
				"\"aaa\",\"b\"\"bb\",\"ccc\"");
		List<String> expected = Arrays.asList("aaa", "b\"bb", "ccc");
		List<String> actual = null;
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		actual = csvFileReader.readRecord();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWithFieldEnclosedWithDoubleQuotesContainingLineBreak() throws IOException {
		StringReader stringReader = new StringReader(
				"\"aaa\",\"b\r\nbb\",\"ccc\"\r\n"
				+ "zzz,yyy,xxx\r\n");
		List<List<String>> expected = Arrays.asList(
				Arrays.asList("aaa", "b\r\nbb", "ccc"),
				Arrays.asList("zzz", "yyy", "xxx"));
		List<List<String>> actual = new ArrayList<List<String>>();
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		List<String> csvRecord = null;
		while ((csvRecord = csvFileReader.readRecord()).size() > 0) {
			actual.add(csvRecord);
		}
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testWithFieldsEnclosedWithDoubleQuotes() throws IOException {
		StringReader stringReader = new StringReader(
				"\"aaa\",\"bbb\",\"ccc\"\r\n"
				+ "zzz,yyy,xxx\r\n");
		List<List<String>> expected = Arrays.asList(
				Arrays.asList("aaa", "bbb", "ccc"),
				Arrays.asList("zzz", "yyy", "xxx"));
		List<List<String>> actual = new ArrayList<List<String>>();
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		List<String> csvRecord = null;
		while ((csvRecord = csvFileReader.readRecord()).size() > 0) {
			actual.add(csvRecord);
		}
		assertEquals(expected, actual);		
	}
	
	@Test(expected = CsvFileReaderException.class)
	public void testWithIncompleteEscapedField() throws IOException {
		StringReader stringReader = new StringReader(
				"\"aaa\",\"bbb\",\"ccc\r\n");
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		csvFileReader.readRecord();		
	}
	
	@Test
	public void testWithNoLineEndingInLastRow() throws IOException {
		StringReader stringReader = new StringReader(
				"aaa,bbb,ccc\r\n"
				+ "zzz,yyy,xxx");
		List<List<String>> expected = Arrays.asList(
				Arrays.asList("aaa", "bbb", "ccc"),
				Arrays.asList("zzz", "yyy", "xxx"));
		List<List<String>> actual = new ArrayList<List<String>>();
		CsvFileReader csvFileReader = new CsvFileReader(stringReader);
		List<String> csvRecord = null;
		while ((csvRecord = csvFileReader.readRecord()).size() > 0) {
			actual.add(csvRecord);
		}
		assertEquals(expected, actual);		
	}

}
