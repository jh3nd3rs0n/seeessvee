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
		csvFileWriter.writeRecord("aaa","bbb","ccc");
		csvFileWriter.writeRecord("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,bbb,ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithFieldContainingCarriageReturn() throws IOException {
		StringWriter stringWriter = new StringWriter();
		CsvFileWriter csvFileWriter = new CsvFileWriter(stringWriter);
		csvFileWriter.writeRecord("aaa","b\rbb","ccc");
		csvFileWriter.writeRecord("xxx", "yyy", "zzz");
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
		csvFileWriter.writeRecord("aaa","b\r\nbb","ccc");
		csvFileWriter.writeRecord("xxx", "yyy", "zzz");
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
		csvFileWriter.writeRecord("aaa","b\nbb","ccc");
		csvFileWriter.writeRecord("xxx", "yyy", "zzz");
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
		csvFileWriter.writeRecord("aaa","b,bb","ccc");
		csvFileWriter.writeRecord("xxx", "yyy", "zzz");
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
		csvFileWriter.writeRecord("aaa","b\"bb","ccc");
		csvFileWriter.writeRecord("xxx", "yyy", "zzz");
		String expected = String.format(
				"aaa,\"b\"\"bb\",ccc%n"
				+ "xxx,yyy,zzz%n");
		String actual = stringWriter.toString();
		assertEquals(expected, actual);
	}
	

}
