package com.codediff;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        HashMap<String, Integer> testHashMap = new HashMap<>();
        Random rng = new Random();

        for (int number = 0; number < 100; number++) {
            testHashMap.put(genRngString(), rng.nextInt());
        }
        System.out.println(testHashMap);
    }

    public static String genRngString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        return new String(array, StandardCharsets.UTF_8);
    }
}
