package com.github.jh3nd3rs0n.seeessvee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FieldTest {

    @Test
    public void testEqualsObjectWithDifferentInstancesEachWithDifferentValue() {
        Field field1 = Field.newInstance("zzz");
        Field field2 = Field.newInstance("aaa");
        assertNotEquals(field1, field2);
    }

    @Test
    public void testEqualsObjectWithDifferentInstancesOfSameValue() {
        Field field1 = Field.newInstance("zzz");
        Field field2 = Field.newInstance("zzz");
        assertEquals(field1, field2);
    }

    @Test
    public void testEqualsObjectWithInstanceAndInstanceOfAnotherClass() {
        Field field1 = Field.newInstance("zzz");
        Object object = new Object();
        assertNotEquals(field1, object);
    }

    @Test
    public void testEqualsObjectWithInstanceAndNull() {
        Field field1 = Field.newInstance("zzz");
        assertNotEquals(null, field1);
    }

    @Test
    public void testEqualsObjectWithSameInstance() {
        Field field1 = Field.newInstance("zzz");
        assertEquals(field1, field1);
    }

    @Test
    public void testHashCode() {
        Field field1 = Field.newInstance("aaa");
        Field field2 = Field.newInstance("aaa");
        assertEquals(field1.hashCode(), field2.hashCode());
    }

}
