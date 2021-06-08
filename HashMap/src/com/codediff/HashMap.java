package com.codediff;


public class HashMap<H, V> {
    public final int INITIAL_SIZE = 10;
    private final int MAX_EXPANSION = 5;
    private H[] hashes;
    private V[] values;
    private int filledSlots = 0;
    private int internalSize = INITIAL_SIZE;


    HashMap() {
        initArrays(internalSize);
    }

    HashMap(int initSize) {
        internalSize = initSize;
        initArrays(internalSize);
    }

    private void initArrays(int arraySizes) {
        hashes = (H[]) new Object[arraySizes];
        values = (V[]) new Object[arraySizes];
    }


    private void resize() {
        H[] newHashes = (H[]) new Object[internalSize * 2];
        V[] newValues = (V[]) new Object[internalSize * 2];

        for (H hash : hashes) {
            int oldIndex = hashingFunction(hash, hashes.length);
            int newIndex = hashingFunction(hash, newHashes.length);
            newValues[newIndex] = values[oldIndex];
        }
        System.arraycopy(hashes, 0, newHashes, 0, hashes.length);

        hashes = newHashes;
        values = newValues;
    }

    private int hashingFunction(H key, int modulo) {
        String keyString = key.toString();
        int total = 0;
        for (Character character : keyString.toCharArray()) {
            total += Integer.parseInt(String.valueOf(character));
        }
        return total % modulo;
    }

    public V getValue(H hash) {
        int index = hashingFunction(hash, hashes.length);
        return values[index];
    }

    public V getValueAt(int index) {
        return values[index];
    }

    private void addHash(H hash) {
        int index = 0;
        while (hashes[index] != null) {
            index++;
        }
        hashes[index] = hash;
    }

    public boolean containsHash(H hash) {
        for (H loopHashes : hashes) {
            if (loopHashes == hash) {
                return true;
            }
        }
        return false;
    }

    /***
     *
     * @param hash
     * @param value
     * @return
     */
    public boolean put(H hash, V value) {
        int index = hashingFunction(hash, internalSize);
        int counter = 0;
        while (values[index] != null && counter < MAX_EXPANSION) {
            resize();
            index = hashingFunction(hash, internalSize);
            counter++;
        }
        if (values[index] != null) {
            return false;
        } else {
            values[index] = value;
            addHash(hash);
            return true;
        }
    }


}
