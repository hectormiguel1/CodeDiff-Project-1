package com.codediff;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {

        HashMap<String, Integer> testHashMap = new HashMap<>();
        ArrayList<String> generatedKeys = new ArrayList<>();
        ArrayList<Integer> generatedInts = new ArrayList<>();
        Random rng = new Random();

        for (int number = 0; number < 10; number++) {
            String generatedString = genRngString();
            Integer generatedInteger = rng.nextInt();
            generatedInts.add(generatedInteger);
            generatedKeys.add(generatedString);
            testHashMap.insert(generatedString, generatedInteger);
        }

        for (int number = 0; number < generatedKeys.size(); number++) {
            System.out.printf("For Key \"%s\" expected value: %d, got: " + testHashMap.getValues(generatedKeys.get(number)) + "\n", generatedKeys.get(number), generatedInts.get(number));
        }
        System.out.println("Keys: " + testHashMap.getKeys());
        System.out.println("Values: " + testHashMap.getValues());
        var randInt = rng.nextInt(generatedKeys.size());
        System.out.printf("Removing Key: \"%s\"\n", generatedKeys.get(randInt));
        testHashMap.remove(generatedKeys.get(randInt));
        System.out.println("Keys: " + testHashMap.getKeys());
        System.out.println("Values: " + testHashMap.getValues());
    }

    public static String genRngString() {
        byte[] array = new byte[1]; // length is bounded by 7
        IntStream.range(0,array.length).forEach(number -> {
            array[number] = (byte) ((byte) 97 + (new Random()).nextInt(5));
        });

        return new String(array, StandardCharsets.US_ASCII);
    }
}
