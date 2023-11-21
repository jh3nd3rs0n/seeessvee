package com.github.jh3nd3rs0n.seeessvee;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

public class CsvFileWriterTest {

	@Test
	public void test() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStrings("aaa","bbb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,bbb,ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithEscapedFields() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStringsToBeEscaped("aaa","bbb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"\"aaa\",\"bbb\",\"ccc\"%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);		
	}

	@Test
	public void testWithFieldContainingCarriageReturn() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStrings("aaa","b\rbb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,\"b\rbb\",ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWithFieldContainingLineBreak() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStrings("aaa","b\r\nbb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,\"b\r\nbb\",ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithFieldContainingLineFeed() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStrings("aaa","b\nbb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,\"b\nbb\",ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithFieldWithComma() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStrings("aaa","b,bb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,\"b,bb\",ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithFieldWithDoubleQuote() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecordFromStrings("aaa","b\"bb","ccc");
		csvFileWriter.writeRecordFromStrings("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,\"b\"\"bb\",ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithNonescapedAndEscapedFields() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecord(
				Field.newEscapedInstance("aaa"),
				Field.newInstance("bbb"),
				Field.newEscapedInstance("ccc"));
		csvFileWriter.writeRecord(
				Field.newInstance("xxx"), 
				Field.newEscapedInstance("yyy"), 
				Field.newInstance("zzz"));
		String expected = String.format(
				"\"aaa\",bbb,\"ccc\"%n"
				+ "xxx,\"yyy\",zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);		
	}
	

}
