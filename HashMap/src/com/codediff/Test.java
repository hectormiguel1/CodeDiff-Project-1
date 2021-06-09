package com.codediff;


public class Test {
    public static void main(String[] args) {
        HashMap<String,Integer> testHashMap = new HashMap<>();
        String key1 = "Hello";
        String key2 = "tester";
        System.out.println("Key1 " + key1.toString());
        System.out.println("Key2 " + key2.toString());
        Integer value3 = 13;
        Integer value1  = 2;
        Integer value2 = 3;
        testHashMap.put(key1, value1);
        testHashMap.put(key1, value2);
        testHashMap.put(key2, value3);
        var values = testHashMap.getValues();
        System.out.println("Values red: " + values + "Length: " + values.size());
        System.out.println("Get Key: " + key1 + " results: " + testHashMap.getValues(key1));
        System.out.println("Get Key: " + key2 + " results: " + testHashMap.getValues(key2));
        testHashMap.remove(key1);
        System.out.println(testHashMap);
    }
}
