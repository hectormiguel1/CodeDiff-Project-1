package com.codediff;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class HashMap <K extends Comparable<K>,V extends Comparable<V>> {
    private final int INITIAL_SIZE = 16;
    private final int HASHING_PRIME = 61;
    private final double DEFAULT_LOAD_FACTOR = 0.65;
    @SuppressWarnings("SortedCollectionWithNonComparableKeys")
    private Set<Key<K>> keys = new TreeSet<>();
    private Node<V>[] values;
    private int filledSlots;
    private double loadFactor= DEFAULT_LOAD_FACTOR;

    HashMap() {
        initArray(INITIAL_SIZE);
    }

    HashMap(int initialSize) {
        initArray(initialSize);
    }
    HashMap(int initialSize, double loadFactor) {
        this.loadFactor = loadFactor;
        initArray(initialSize);
    }
    HashMap(double loadFactor) {
        this.loadFactor = loadFactor;
        this.initArray(INITIAL_SIZE);
    }

    private void initArray(int initSize) {
        values = (Node<V>[]) new Node<?>[initSize];
    }

    private void resize() {
        Node<V>[] newValues = (Node<V>[]) new Node<?>[values.length * 2];
        for (var key : getKeys()) {
            int oldMapping = key.getMappingIndex();
            int newIndex = getNewMapping(key, newValues.length);
            newValues[newIndex] = values[oldMapping];
        }
        values = newValues;
    }

    public ArrayList<Key<K>> getKeys() {
        return new ArrayList<>(keys);
    }

    public boolean containsKey(K key) {
        return keys.contains(new Key<K>(key));
    }

    private int getMapping(Key<K> key) {
        //Does Modulo with Values Length
        return (key.hashCode() % (values.length % HASHING_PRIME));
    }

    private int getNewMapping(Key<K> key, int newSize) {
        return (key.hashCode() % (newSize % HASHING_PRIME));
    }

    public ArrayList<V> getValues() {
        ArrayList<V> returnValues = new ArrayList<>();
        for(Node<V> val : values) {
            Node<V> currentNode = val;
            if (currentNode != null) {
                Node<V> nextNode = currentNode.getNextValue();
                while (nextNode != null) {
                    returnValues.add(currentNode.getValue());
                    currentNode = nextNode;
                    nextNode = currentNode.getNextValue();
                }
                returnValues.add(currentNode.getValue());
            }
        }
        return returnValues;
    }

    public void put(K key, V value) {
        double currentLoad = filledSlots  / (double)values.length;
        if(currentLoad >= loadFactor) {
            resize();
        }
        int mappingIndex= getMapping(new Key<K>(key));
        if(values[mappingIndex] == null) {
            filledSlots++;
            addKey(key);
            values[mappingIndex] = new Node<V>(value);
        } else {
            filledSlots++;
            addKey(key);
            insertNode(value, mappingIndex);
        }
    }

    private void insertNode(V value, int index) {
        Node<V> newNode = new Node<V>(value);
        Node<V> currentNode = values[index];
        Node<V> nextNode = currentNode.getNextValue();
        while(nextNode != null) {
            currentNode = nextNode;
            nextNode = currentNode.getNextValue();
        }
        currentNode.setNextValue(newNode);
    }

    private void addKey(K key) {
        Key<K> newKey = new Key<K>(key);
        int mappingIndex = getMapping(newKey);
        newKey.setMappingIndex(mappingIndex);
        keys.add(newKey);
    }

    public ArrayList<V> getValues(K key) {
        int index = getMapping(new Key<K>(key));
        if(values[index] == null) {
            new ArrayList<>();
        } else {
            Node<V> currentNode = values[index];
            Node<V> nextNode = currentNode.getNextValue();
            ArrayList<V> returnValues = new ArrayList<>();
            while(nextNode != null) {
                returnValues.add(currentNode.getValue());
                currentNode = nextNode;
                nextNode = currentNode.getNextValue();
            }
            returnValues.add(currentNode.getValue());
        }
        return new ArrayList<>();
    }

    public boolean containsValue(V value) {
        for (var _value : values) {
            if (_value != null) {
                if (_value.getValue().equals(value)) {
                    return true;
                } else if (_value.nextValue != null) {
                    var currentNode = _value;
                    var nextNode = currentNode.getNextValue();
                    while (nextNode != null) {
                        if (currentNode.getValue().equals(value)) {
                            return true;
                        }
                        currentNode = nextNode;
                        nextNode = currentNode.getNextValue();
                    }
                }
            }
        }
        return false;
    }

    /***
     *
     * @param value
     * @return
     */
    final int VALUE_NOT_FOUND_SENTINAL = -1;
    public int getValueIndex(V value) {
        for (int index = 0; index < values.length; index++) {
            if (values[index] != null) {
                if (values[index].getNextValue().getValue().equals(value)) {
                    return index;
                } else if (values[index].nextValue != null) {
                    Node<V> currentNode = values[index];
                    Node<V> nextNode = currentNode.getNextValue();
                    while (nextNode != null) {
                        if (currentNode.getValue().equals(value)) {
                            return index;
                        } else {
                            currentNode = nextNode;
                            nextNode = currentNode;
                        }
                    }
                }
            }
        }
        return VALUE_NOT_FOUND_SENTINAL;
    }

    public double getCurrentLoad() {
        return filledSlots / (double)values.length;
    }

    public int getFilledSlots() {
        return filledSlots;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    @Override
    public String toString() {
        return "HashMap{" +
                "keys=" + keys.toString() +
                ",\nValues=" + Arrays.toString(values) +
                ",\nFilledSlots=" + filledSlots +
                ",\nLoadFactor=" + loadFactor +
                '}';
    }
}

class Key<K> implements Comparable<Key<K>>{
    K key;
    int mappingIndex;

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

class Node<V> {
    V value;
    Node<V> nextValue;

    Node(V value) {
        this.value = value;
    }

    public V getValue() {
        return this.value;
    }

    public void setNextValue(Node<V> nextValue) {
        this.nextValue = nextValue;
    }

    public Node<V> getNextValue() {
        return this.nextValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?> node = (Node<?>) o;

        return value.equals(node.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", nextValue=" + nextValue +
                "}\n";
    }
}