package com.codediff;


import java.util.Arrays;

public class HashMap<K, V> {
    public final int INITIAL_SIZE = 10;
    private final int MAX_EXPANSION = 5;
    private K[] keys;
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

    public K[] getKeys() {
        return keys;
    }

    public V[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "HashMap{" +
                "keys=" + Arrays.toString(keys) +
                ", values=" + Arrays.toString(values) +
                '}';
    }

    private void initArrays(int arraySizes) {
        keys = (K[]) new Object[arraySizes];
        values = (V[]) new Object[arraySizes];
    }


    private void resize() {
        K[] newHashes = (K[]) new Object[internalSize * 2];
        V[] newValues = (V[]) new Object[internalSize * 2];

        for (K key : keys) {
            int oldIndex = hashingFunction(key, keys.length);
            int newIndex = hashingFunction(key, newHashes.length);
            newValues[newIndex] = values[oldIndex];
        }
        System.arraycopy(keys, 0, newHashes, 0, keys.length);

        keys = newHashes;
        values = newValues;
    }

    private int hashingFunction(K key, int modulo) {
        String keyString = key.toString();
        int total = 0;
        for (Character character : keyString.toCharArray()) {
            total += Integer.parseInt(String.valueOf(character));
        }
        return total % modulo;
    }

    public V getValue(K hash) {
        int index = hashingFunction(hash, keys.length);
        return values[index];
    }

    public V getValueAt(int index) {
        return values[index];
    }

    private void addKey(K hash) {
        int index = 0;
        while (keys[index] != null) {
            index++;
        }
        keys[index] = hash;
    }

    public boolean containsKey(K hash) {
        for (K _keys : keys) {
            if (_keys.equals(hash)) {
                return true;
            }
        }
        return false;
    }

    /***
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(K key, V value) {
        int index = hashingFunction(key, internalSize);
        int counter = 0;
        while (keys[index] != null && counter < MAX_EXPANSION) {
            resize();
            index = hashingFunction(key, internalSize);
            counter++;
        }
        if (values[index] != null) {
            return false;
        } else {
            if(values[index].equals(value)) {
                addKey(key);
                filledSlots++;
            } else {
                filledSlots++;
                values[index] = value;
                addKey(key);
            }
            return true;
        }
    }

    private void removeKey(K hash) {
        for (int index = 0; index < keys.length; index++) {
            if(keys[index].equals(hash)) {
                keys[index] = null;
            }
        }
    }

    public void remove(K hash) {
        if(containsKey(hash)) {
            int index = hashingFunction(hash, internalSize);
            keys[index] = null;
            removeKey(hash);
        }
    }

    public double getLoad() {
        return filledSlots / (double)internalSize;
    }

}
