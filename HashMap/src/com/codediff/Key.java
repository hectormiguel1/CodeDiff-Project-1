package com.codediff;

import org.jetbrains.annotations.NotNull;

public class Key<K> implements Comparable<Key<K>>{
    K key;
    int mappingIndex;
    int numItemsMapped;

    Key(K key) {
        this.key = key;
    }

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

    public void setMappingIndex(int mappingIndex) {
        this.mappingIndex = mappingIndex;
    }

    public int getMappingIndex() {
        return mappingIndex;
    }

    public int getNumItemsMapped() {
        return numItemsMapped;
    }

    public void incrementItemsMapped() {
        numItemsMapped++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key<?> key1 = (Key<?>) o;

        if (mappingIndex != key1.mappingIndex) return false;
        if(this.hashCode() != key1.hashCode()) return false;
        return this.hashCode() == o.hashCode();
    }

    public K getKey() {
        return key;
    }

    @Override
    public int compareTo(@NotNull Key<K> otherKey) {
        return this.hashCode() - otherKey.hashCode();
    }

    @Override
    public String toString() {
        return "\nKey{" +
                "key=" + key +
                ", mappingIndex=" + mappingIndex +
                "}";
    }

}