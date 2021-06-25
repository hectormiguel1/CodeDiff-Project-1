package com.codediff;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HashMap <K extends Comparable<K>,V extends Comparable<V>>{
    private final int INITIAL_SIZE = 16;
    private final double DEFAULT_LOAD_FACTOR = 0.65;
    private final TreeSet<Key<K>> keys = new TreeSet<>();
    private Node<V>[] values;
    private int filledSlots;
    private double loadFactor = DEFAULT_LOAD_FACTOR;

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

    /***
     * Used to initialize the internal array structure which holds the values.
     * @param initSize: Initial size of the array.
     */
    private void initArray(int initSize) {
        values = (Node<V>[]) new Node<?>[initSize];
    }



    public int size() {
        return filledSlots;
    }


    public boolean isEmpty() {
        return filledSlots == 0;
    }

    /***
     * Function is un charge of resizing the internal array which hold the values linkedLists.
     * We do this by coping our current values into an old array, then resize the new array to be twice the size
     * it currently is, then we iterate through each node in the old values array and reinsert them into the new values
     * array.
     */
    private void resize() {
        //Copy values into tmp array
        Node<V>[] oldArr = Arrays.copyOf(values, values.length);
        //resize the array and empty it out.
        values = (Node<V>[]) new Node<?>[values.length * 2];
        //reset the keys, keys will be reinserted on put.
        keys.clear();
        //reset the current items in the array, this is updated by put.
        filledSlots = 0;
        //Loop through each surface node in the values array.
        for (Node<V> value : oldArr) {
            if (value != null) {
                //If the node is not null, init variables to traverse nested Nodes.
                Node<V> currentNode = value;
                Node<V> nextNode = value.getNextValue();
                //Loop through nested nodes while the nextNode in the chain is not null.
                while (nextNode != null) {
                    //ReVerify that the currentNode is not null
                    assert currentNode != null;
                    //Insert the current node we are working with.
                    put((K) currentNode.getKeyObject().getKey(), currentNode.getValue());
                    //Updates currentNode and NextNode to move to next nested Node.
                    currentNode = currentNode.getNextValue();
                    if (currentNode != null) {
                        nextNode = currentNode.getNextValue();
                    }
                }
                //Insert the surface node.
                put((K) currentNode.getKeyObject().getKey(), currentNode.getValue());
            }
        }

    }

    /**
     * Returns all they keys currently in the hash map
     *
     * @return: ArrayList containing Keys.
     */
    public Set<K> keySet() {
        TreeSet<K> returnKeys = new TreeSet<>();
        keys.forEach(key -> returnKeys.add(key.getKey()));
        return returnKeys;
    }

    /**
     * Utility function to check if the current key is already in the hash map.
     *
     * @param key: Key to test for.
     * @return True if key is present, false otherwise.
     */

    public boolean containsKey(Object key) {
        return keys.contains(new Key<>(key));
    }

    /**
     * Function maps the hashcode for they key to an index in the values array.
     *
     * @param key: Key to get mapping for.
     * @return: Index the Key maps to.
     */
    private int getMapping(Key<K> key) {
        //Does Modulo with Values Length
        return (key.hashCode() % (values.length));
    }

    /**
     * Function returns all they values currently in the hash Map, these values are return in their order,
     * IE all nodes in index 0, then all nodes in index 1, .......
     *
     * @return: ArrayList of Values.
     */
    public ArrayList<V> values() {
        ArrayList<V> returnValues = new ArrayList<>();
        for (Node<V> val : values) {
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

    /**
     * Function used to add keys and values to the hash map.
     *
     * @param key:   key the value belongs to.
     * @param value: value to be inserted.
     */
    public V put(K key, V value) {
        //Check the current load of the the map
        double currentLoad = filledSlots / (double) values.length;
        //If the load is too big, resize the structure to avoid collisions.
        if (currentLoad >= loadFactor) {
            resize();
        }
        //get mapping for new Key
        Key<K> newKey = new Key<>(key);
        int mappingIndex = getMapping(newKey);
        newKey.setMappingIndex(mappingIndex);
        for (Key<K> element : keys) {
            if (element.getKey().equals(key)) {
                newKey = element;
            }
        }
        mappingIndex = newKey.getMappingIndex();
        //Check if the new value can be inserted as a surface node, if fo insert it as a surface node.
        if (values[mappingIndex] == null) {
            //Wrap the value in a Node of the Value type.
            Node<V> newNode = new Node<>(value);
            newNode.setKey(newKey);
            //Insert the node at the index Surface.
            values[mappingIndex] = newNode;
        } else {
            //If the Surface node is occupied, insert into the next node in that chain.
            insertNode(value, mappingIndex, newKey);
        }
        //Increase number slots filled in the map.
        filledSlots++;
        //add the new key to the key set.
        if(!containsKey(newKey)) {
            addKey(newKey);
        }

        return value;
    }

    /**
     * Utility Function to remove a node from values array.
     *
     * @param key: Key owner of the node to remove.
     */
    private void removeNode(Key<K> key) {
        //Check if the node is at the surface and there are no nodes after the surface node.
        if (values[key.getMappingIndex()].getKeyObject().equals(key) && values[key.getMappingIndex()].getNextValue() == null) {
            //If node is at surface, remove by setting values at index equals to null.
            values[key.getMappingIndex()] = null;
        } else {
            //Begin iterating through the nodes, Keeping track of previous node, currentNode, and the Next Node.
            Node<V> prevNode = null;
            Node<V> currentNode = values[key.getMappingIndex()];
            Node<V> nextNode = currentNode.getNextValue();
            //Keep tracks of items removed
            int removedItems = 0;
            //iterate over nodes while we still have items to remove and we have not reached the end of the nodes.
            while (removedItems != key.getNumItemsMapped() && currentNode != null) {
                //Check if the key controller of the current node equals the key of the nodes we want to remove.
                boolean foundInCurrentNode = currentNode.getKeyObject().equals(key);
                //If it does and the previous node is null, then current node is surface, and we can set the
                //surface node to the next node in the chain
                if (foundInCurrentNode && prevNode == null) {
                    values[key.getMappingIndex()] = nextNode;
                    removedItems++;
                    filledSlots--;
                }
                //If current node is node to be removed and previous node is not null, then we can set
                //the next node previous points to equal the next node in the chain. thus removing the current node from the
                //chain.
                else if (foundInCurrentNode) {
                    prevNode.setNextValue(nextNode);
                    removedItems++;
                    filledSlots--;
                }
                //If the current node is not the node to be removed update the previous node to equal the current node.
                else {
                    prevNode = currentNode;
                }
                //Update current node and next node to move to next set of nodes.
                currentNode = nextNode;
                if (currentNode != null) {
                    nextNode = currentNode.getNextValue();
                }
            }
        }
    }

    /***
     * Function used to remove the key from the Hash Map, this will also remove all the Values associated with that key.
     * @param key : Key to remove from Hash Map.
     * @return: the first value attacked to the key. All items are removed though.
     */
    public V remove(Object key) {
        //Hold on to the key we need to remove, Using arraylist to get around local variables and lambda expression limitations
        ArrayList<Key<K>> keysToRemove = new ArrayList<>();
        V returnValue = get(key);
        //Iterate over each key in the set
        keys.forEach(element -> {
            //if the keys are equal, remove the nodes attached to it, add the key to the list of keys to remove.
            if (element.getKey().equals(key)) {
                removeNode(element);
                keysToRemove.add(element);
            }
        });
        //Remove all the keys added to keys to remove list.
        keysToRemove.forEach(keys::remove);
        return returnValue;
       // return null;
    }

    public void clear() {
        keys.clear();
        values = (Node<V>[]) new Node<?>[INITIAL_SIZE];
        filledSlots = 0;
    }

    /***
     * Function used when inserting a node into an already existing Node chain.
     * @param value: Value to be inserted in the Node Chain
     * @param index: index the Key owner of value is mapped to.
     * @param key: key owner of the value.
     */
    private void insertNode(V value, int index, Key<K> key) {
        //Create Node from value
        Node<V> newNode = new Node<>(value);
        //Set owner key
        newNode.setKey(key);
        //Being iterating over the nodes at the mapped index.
        Node<V> currentNode = values[index];
        Node<V> nextNode = currentNode.getNextValue();
        while (nextNode != null) {
            currentNode = nextNode;
            nextNode = currentNode.getNextValue();
        }
        //Insert node at the next available space.
        currentNode.setNextValue(newNode);
    }

    /**
     * Function to add the key to the the set, if the key already is in the set, increment the number of values the key points to.
     *
     * @param key: Key to be added.
     */
    private void addKey(Key<K> key) {
        //Increment items mapped, a key will always point to at least one value
        key.incrementItemsMapped();
        //Check if the key is in the current set
        if (keys.contains(key)) {
            //if key is in the set, increment the number of items the key maps to
            keys.forEach(element -> {
                if (element.equals(key)) {
                    element.incrementItemsMapped();
                }
            });
        }
        //Otherwise simply add the key to set.
        else {
            keys.add(key);
        }
    }

    /***
     * Function returns all the values in the values array which are mapped to the passed key.
     * @param key: Key of values.
     * @return: ArrayList of values which belong to that key. Returns empty array if key not found.
     */
    public ArrayList<V> values(K key) {
        Key<K> _key = new Key<>(key);
        int index = getMapping(_key);
        _key.setMappingIndex(index);
        //Check if key is already in the Hash Map, if it does not, we can return an empty list.
        if (!containsKey(key)) {
            return new ArrayList<>();
        } else {
            //If key exits in the hack map, prepare to iterate over the nodes in the mappedIndex.
            Node<V> currentNode = values[index];
            Node<V> nextNode = currentNode.getNextValue();
            //Store the values in a temporary arrayList.
            ArrayList<V> returnValues = new ArrayList<>();
            while (currentNode.getNextValue() != null) {
                //Check if the current Node owner Key equals the Key we are searching for
                boolean keysEqual = currentNode.getKeyObject().equals(_key);
                //If owner key is equals to the key we are searching for, add the current nodes value to the return list.
                if (keysEqual) {
                    returnValues.add(currentNode.getValue());
                }
                //otherwise update references to iterate over next set of nodes.
                currentNode = nextNode;
                nextNode = currentNode.getNextValue();
            }
            //If the node is a surface nodes, add it to the return arraylist
            if (currentNode.getKeyObject().equals(_key)) {
                returnValues.add(currentNode.getValue());
            }
            return returnValues;
        }
    }

    /***
     * Utility function to return the first value attached to the key.
     * @param key: Key owner of the value
     * @return: Value type first value found for the Key, returns empty array if not found.
     */
    public V get(Object key) {
        try {
            return new LinkedList<>(values((K) key)).getFirst();
        } catch(NoSuchElementException ex) {
            return null;
        }
    }

    /***
     * Utility Function to check the the passed value is currently in the hash map.
     * @param value: value to check for.
     * @return: true if the value was found, false otherwise.
     */
    public boolean containsValue(Object value) {
        //Iterate over the values.
        for (var _value : values) {
            if (_value != null) {
                //if the current surface node is not null, check if surface node is value
                if (_value.getValue().equals(value)) {
                    return true;
                }
                //If surface node is not value, iterate over the nodes in chain to check if value is present.
                else if (_value.getNextValue() != null) {
                    var currentNode = _value;
                    var nextNode = currentNode.getNextValue();
                    while (nextNode != null) {
                        //Check each node on the chain to see if it is the value.
                        if (currentNode.getValue().equals(value)) {
                            return true;
                        }
                        //Update references to iterate over the chain.
                        currentNode = nextNode;
                        nextNode = currentNode.getNextValue();
                    }
                }
            }
        }
        //if the value was not found.
        return false;
    }

    /***
     * Read the current Load on the HashMap.
     * @return: current load on the hashmap.
     */
    public double getCurrentLoad() {
        return filledSlots / (double) values.length;
    }

    /**
     * Get the current number of slots filled on the map.
     * @return: filledSlots.
     */
    public int getFilledSlots() {
        return filledSlots;
    }

    /***
     * Get the current load factor used when deciding when to auto expand the hash map.
     * @return: current load factor (value between 0 and 1).
     */
    public double getLoadFactor() {
        return loadFactor;
    }

    /***
     * Update the current load factor..
     * @param loadFactor: new load factor values, values between (0 and 1) expected.
     */
    public void setLoadFactor(double loadFactor) {
       if(loadFactor < 1 && loadFactor > 0) {
           this.loadFactor = loadFactor;
       }
    }

    /***
     * Creates a string version of the Map.
     * @return: returns string representation of the map.
     */
    @Override
    public String toString() {
        return "HashMap{" +
                "\n\tkeys=" + keys +
                ",\n\tValues=" + Arrays.toString(values) +
                ",\n\tFilledSlots=" + filledSlots +
                ",\n\tLoadFactor=" + loadFactor +
                ",\n\tCurrentLoad=" + (filledSlots / (double) values.length) +
                ",\n\tSize=" + values.length +
                '}';
    }
}



