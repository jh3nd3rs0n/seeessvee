package com.github.jh3nd3rs0n.seeessvee;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldTest {

	@Test
	public void testEqualsObjectWithDifferentInstancesEachWithDifferentValue() {
		Field field1 = Field.newInstance("zzz");
		Field field2 = Field.newInstance("aaa");
		assertTrue(!field1.equals(field2));
	}

	@Test
	public void testEqualsObjectWithDifferentInstancesOfSameValue() {
		Field field1 = Field.newInstance("zzz");
		Field field2 = Field.newInstance("zzz");
		assertTrue(field1.equals(field2));
	}

	@Test
	public void testEqualsObjectWithInstanceAndInstanceOfAnotherClass() {
		Field field1 = Field.newInstance("zzz");
		Object object = new Object();
		assertTrue(!field1.equals(object));
	}

	@Test
	public void testEqualsObjectWithInstanceAndNull() {
		Field field1 = Field.newInstance("zzz");
		assertTrue(!field1.equals(null));
	}

	@Test
	public void testEqualsObjectWithSameInstance() {
		Field field1 = Field.newInstance("zzz");
		assertTrue(field1.equals(field1));
	}

	@Test
	public void testHashCode() {
		Field field1 = Field.newInstance("aaa");
		Field field2 = Field.newInstance("aaa");
		assertTrue(field1.hashCode() == field2.hashCode());
	}

}
