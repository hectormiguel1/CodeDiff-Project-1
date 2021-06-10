package com.codediff;

import org.jetbrains.annotations.NotNull;

/***
 * Object will Hold keys in the Hash map as well as other information about the keys, such as the number of items that
 *  key points to and the index where the key maps to.
 * @param <K>: Datatype of the Key object being used.
 */
public class Key<K> implements Comparable<Key<K>>{
    private final K key;
    private int mappingIndex;
    private int numItemsMapped;

    Key(K key) {
        this.key = key;
    }


    /***
     * Generates the hashcode for the key.
     * For this example implementation we are keeping it simple and adding up the values of the .toString() characters.
     */
    @Override
    public int hashCode() {
        char[] characters = key.toString().toCharArray();
        int total = 0;
        //Add up ascii character values;
        for (char character : characters) {
            total += character;
        }

        return total;
    }

    /**
     * Setter for the mappingIndex
     * @param mappingIndex: new mapping index.
     */
    public void setMappingIndex(int mappingIndex) {
        this.mappingIndex = mappingIndex;
    }

    /***
     * Getter for the currentMapping index.
     * @return: Integer, the current location the key points to.
     */
    public int getMappingIndex() {
        return mappingIndex;
    }

    /**
     * Getter for the number of items the key points to.
     * @return: Integer number of items the key points to.
     */
    public int getNumItemsMapped() {
        return numItemsMapped;
    }

    /**
     * Increments the number of items the key points to.
     */
    public void incrementItemsMapped() {
        numItemsMapped++;
    }

    /***
     * Compares the current instance of Key to another Object.
     * @param o: Other Object.
     * @return: True if objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key<?> key1 = (Key<?>) o;
        return mappingIndex == key1.mappingIndex && hashCode() == key1.hashCode();
    }

    /**
     * Getter for the Key Object
     * @return: returns the Internal Key object.
     */
    public K getKey() {
        return key;
    }

    /**
     * Used to compare one Key to another Key
     * @param otherKey: Key to compare against.
     * @return Integer  < 0 if other key has higher hashCode, 0 if same, > 0 if lower than.
     */
    @Override
    public int compareTo(@NotNull Key<K> otherKey) {
        return this.hashCode() - otherKey.hashCode();
    }

    /**
     * String representation of the Key
     * @return Stringified version of the object.
     */
    @Override
    public String toString() {
        return "\nKey{" +
                "key=" + key +
                ", mappingIndex=" + mappingIndex +
                ", numberOfItemMapped=" + numItemsMapped +
                "}";
    }

}