package com.codediff;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class HashMapTest {
     private HashMap<String, String> testMap;

    @Before
    public void setUp() {
        testMap = new HashMap<>();
    }

    @Test
    public void testSizeWithItem() {
        //Insert Item
        testMap.put("Hello", "World");
        int expected = 1;
        int actual = testMap.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSizeWithoutItem() {
        int expected = 0;
        int actual =  testMap.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsEmptyWithoutItem() {
        boolean expected = true;
        boolean actual = testMap.isEmpty();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testIsEmptyWithItem() {
        testMap.put("Hello", "World");
        boolean expected = false;
        boolean actual = testMap.isEmpty();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testKeySetEmptyHashMap() {
        var Keys = testMap.keySet();
        var expected = new TreeSet<String>();
        Assert.assertEquals(expected, Keys);
    }
    @Test
    public void testKeySetNonEmptyHashMap() {
        testMap.put("Hello", "World");
        var Keys = testMap.keySet();
        var expected = new TreeSet<String>();
        expected.add("Hello");
        Assert.assertEquals(expected, Keys);
    }

    @Test
    public void testContainsKeyEmptyHashMap() {
        String key = "Hello";
        boolean expected = false;
        boolean actual = testMap.containsKey(key);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testContainsKeyHashMap() {
        String key = "Hello";
        testMap.put(key, "World");
        boolean expected = true;
        boolean actual = testMap.containsKey(key);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValuesEmptySet() {
        var expected = new ArrayList<String>();
        var actual = testMap.values();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testValuesNonEmptySet() {
        String key = "Hello";
        String value = "World";
        testMap.put(key,value);
        var expected = new ArrayList<String>();
        expected.add(value);
        var actual = testMap.values();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPutOnEmpty() {
        String key1 = "Hello";
        String key2 = "World";
        String value1 = "Test";
        String value2 = "Test2";
        var actual = testMap.put(key1, value1);
        var expected = value1;
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void testPutOnNonEmpty() {
        String key1 = "Hello";
        String key2 = "World";
        String value1 = "Test";
        String value2 = "Test2";
        testMap.put(key2, value2);
        var actual = testMap.put(key1, value1);
        var expected = value1;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testRemoveOnEmpty() {
        String key1 = "Hello";
            var actual = testMap.remove(key1);
            String expected = null;
            //On empty map, this should always throw exception
            Assert.assertEquals(expected, actual);
    }
    @Test
    public void testRemoveOnNonEmpty() {
        try{
            String key1 = "Hello";
            testMap.put(key1, "Test");
            var expected = "Test";
            var actual = testMap.remove(key1);
            Assert.assertEquals(expected, actual);
        }catch(NoSuchElementException ex) {
            Assert.fail();
        }
    }

    @Test
    public void testClearOnEmpty() {
        var expected = true;
        testMap.clear();
        var actual = testMap.isEmpty();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testClearOnNonEmpty() {
        testMap.put("String", "String2");
        var expected = true;
        testMap.clear();
        var actual = testMap.isEmpty();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValuesOnEmpty() {
        var expected = 0;
        var actual = testMap.values().size();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testValuesOnNonEmpty() {
        var expected = 0;
        var actual = testMap.values().size();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testValuesOnEmptyWithKey() {
        String key1 = "Hello";
        var expected = 0;
        var actual = testMap.values(key1).size();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testValuesOnNonEmptyWithKey() {
        String key1 = "Hello";
        testMap.put(key1, "Hello");
        var expected = 1;
        var actual = testMap.values(key1).size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetOnEmpty() {
        String key1 = "Hello";
        String expected = null;
        var actual = testMap.get(key1);
    }
    @Test
    public void testGetOnNonEmpty() {
        String key1 = "Hello";
        testMap.put(key1, "Test");
        String expected = null;
        var actual = testMap.get(key1);
    }

    @Test
    public void testContainsValueOnEmpty() {
        String value = "Test";
        boolean expected = false;
        boolean actual = testMap.containsValue(value);
        Assert.assertEquals(actual, expected);
    }
    @Test
    public void testContainsValueOnNonEmpty() {
        HashMap<Integer, String> testMap = new HashMap<>();
        ArrayList<Integer> keys = new ArrayList<>();
        keys.add(1);
        keys.add(1);
        ArrayList<String> values = new ArrayList<>();
        values.add("Value1");
        values.add("Value2");
        for(int index = 0; index < keys.size(); index++) {
            testMap.put(keys.get(index), values.get(index));
        }
        boolean expected = true;
        boolean actual = testMap.containsValue("Value1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentLoadOnEmpty() {
        double expected = 0.0;
        double deltaDiffAllowed = 0.0001;
        double actual = testMap.getCurrentLoad();
        Assert.assertEquals(expected, actual, deltaDiffAllowed);
    }
    @Test
    public void testGetCurrentLoadOnNonEmpty() {
        testMap.put("Test", "Test2");
        double expected = 1/16.0;
        double deltaDiffAllowed = 0.0001;
        double actual = testMap.getCurrentLoad();
        Assert.assertEquals(expected, actual, deltaDiffAllowed);
    }

    @Test
    public void testGetFilledSlotsOnEmpty() {
        int expected = 0;
        int actual = testMap.getFilledSlots();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testGetFilledSlotsOnNonEmpty() {
        testMap.put("Test", "Test2");
        int expected = 1;
        int actual = testMap.getFilledSlots();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetLoadFactor() {
        double expected =  0.65;
        double actual = testMap.getLoadFactor();
        double deltaDiffAllowed = 0.0001;
        Assert.assertEquals(expected, actual, deltaDiffAllowed);

    }

    @Test
    public void testSetLoadFactor() {
        double expected = 0.80;
        testMap.setLoadFactor(0.80);
        double actual = testMap.getLoadFactor();
        double deltaDiffAllowed = 0.0001;
        Assert.assertEquals(expected, actual, deltaDiffAllowed);
    }

    @Test
    public void testResize() {
        testMap = new HashMap<String, String>(1);
        ArrayList<String> keys = new ArrayList<>();
        keys.add("1");
        keys.add("2");
        ArrayList<String> values = new ArrayList<>();
        values.add("Value1");
        values.add("Value2");
        HashMap<String, String> testMap2 = new HashMap<>(5);

        for(int index = 0; index < keys.size(); index++) {
            testMap.put(keys.get(index), values.get(index));
            testMap2.put(keys.get(index), values.get(index));
        }
        var expectedValues = testMap2.values();
        var actualValues = testMap.values();

        Assert.assertEquals(expectedValues, actualValues);
    }

    @Test
    public void testInsertNode() {
        ArrayList<String> keys = new ArrayList<>();
        keys.add("1");
        keys.add("1");
        ArrayList<String> values = new ArrayList<>();
        values.add("Value1");
        values.add("Value2");
        for(int index = 0; index < keys.size(); index++) {
            testMap.put(keys.get(index), values.get(index));
        }
        var expectedValues = values;
        var actualValues = testMap.values("1");

        Assert.assertEquals(expectedValues, actualValues);
    }

    @Test
    public void testRemoveNode() {
        HashMap<Integer, String> testMap = new HashMap<Integer, String>();
        ArrayList<Integer> keys = new ArrayList<>();
        keys.add(1);
        keys.add(1);
        keys.add(1);
        keys.add(1);
        keys.add(17);
        ArrayList<String> values = new ArrayList<>();
        values.add("Value4");
        values.add("Value5");
        values.add("Value1");
        values.add("Value2");
        values.add("Value3");

        for(int index = 0; index < keys.size(); index++) {
            testMap.put(keys.get(index), values.get(index));
        }
        testMap.remove(1);
        var expectedValues = new ArrayList<String>();
        expectedValues.add("Value3");
        var actualValues = testMap.values();

        Assert.assertEquals(expectedValues, actualValues);
    }




}