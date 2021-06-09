package com.codediff;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class HashMap <K extends Comparable<K>,V extends Comparable<V>> {
    private final int INITIAL_SIZE = 16;
    private final int HASHING_PRIME = 61;
    private final double DEFAULT_LOAD_FACTOR = 0.65;
    private final TreeSet<Key<K>> keys = new TreeSet<>();
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
        return (key.hashCode() % (values.length ));
    }

    private int getNewMapping(Key<K> key, int newSize) {
        return (key.hashCode() % (newSize));
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
        Key<K> newKey = new Key<K>(key);
        int mappingIndex= getMapping(newKey);
        if(values[mappingIndex] == null) {
            filledSlots++;
            addKey(newKey);
            Node<V> newNode = new Node<V>(value);
            newNode.setKey(newKey);
            values[mappingIndex] = newNode;
        } else {
            filledSlots++;
            addKey(newKey);
            insertNode(value, mappingIndex, newKey);
        }
    }

    private void removeNode(Key<K> key) {
        if (values[key.getMappingIndex()].getKey().equals(key) && values[key.getMappingIndex()].getNextValue() == null) {
            values[key.getMappingIndex()] = null;
        } else {
            Node<V> prevNode = null;
            Node<V> currentNode = values[key.getMappingIndex()];
            Node<V> nextNode = currentNode.getNextValue();
            int removedItems = 0;
            while (removedItems != key.getNumItemsMapped() && currentNode != null) {
                boolean foundInCurrentNode = currentNode.getKey().equals(key);
                if (foundInCurrentNode && prevNode == null) {
                    values[key.getMappingIndex()] = nextNode;
                    removedItems++;
                    filledSlots--;
                } else if (foundInCurrentNode) {
                    prevNode.setNextValue(nextNode);
                    removedItems++;
                    filledSlots--;
                } else {
                    prevNode = currentNode;

                }
                currentNode = nextNode;
                if (currentNode != null) {
                    nextNode = currentNode.getNextValue();
                }


            }

        }

    }

    public void remove(K key) {
        AtomicReference<Key<K>> keyToRemove = new AtomicReference<>(new Key<K>(key));
        keys.forEach(element -> {
            if(element.getKey().equals(key)) {
                removeNode(element);
                keyToRemove.set(element);
            }
        });
        keys.remove(keyToRemove.get());
    }

    private void insertNode(V value, int index, Key<K> key) {
        Node<V> newNode = new Node<V>(value);
        newNode.setKey(key);
        Node<V> currentNode = values[index];
        Node<V> nextNode = currentNode.getNextValue();
        while(nextNode != null) {
            currentNode = nextNode;
            nextNode = currentNode.getNextValue();
        }
        currentNode.setNextValue(newNode);
    }

    private void addKey(Key<K> key) {
        int mappingIndex = getMapping(key);
        key.setMappingIndex(mappingIndex);
        key.incrementItemsMapped();
        if (keys.contains(key)) {
            keys.forEach(element -> {
                if (element.equals(key)) {
                    element.incrementItemsMapped();
                    ;
                }
            });
        } else {
            keys.add(key);
        }
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
            return returnValues;
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
                ",\nCurrentLoad=" + (filledSlots/(double) values.length) +
                ",\nSize=" + values.length +
                '}';
    }
}



