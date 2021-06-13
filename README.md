# Java Data Structure Tutorial Project

## HashMap

### How it works/stores data

*(Only real programmers can read image)*

<img src="https://github.com/hectormiguel1/CodeDiff-Project-1/raw/main/graphics/hashmap.png"></img>

A Hashmap is a data structure that implementates the interface Map of Java. It stores data using the Key and Value relationship. You can not have duplicated key in the same hashmap but dupcated values are allowed. Keys are stored in a set and the values are store in a chain of nodes. You can acces the values using the keys. 

### How a client interacts with it (interface)

To acces an elements from the hashmap the client can use the key to store and retreive a values. They also have the option to get all the keys and all the values in that hashmap.
   
### The pros and cons of using the data structure

|        Pro    |    
| ------------- |
| Hashsmaps allow Key and value combinations to be inserted | 
| HashMaps are fail-fast iterator.  Meaning during iteration it fails as soon as it recognized the collection's structure has changed. | 
| Hashing technology allows for faster access to elements. | 
| HashMap is a non-synchronized data structure. Which means that without proper synchronization, HashMap cannot be shared between many threads. To avoid accidental unsynchronized access to the map: ```Map m = Collections.synchronizedMap(new HashMap(...)); ``` |

|        Cons    |    
| ------------- |
| When two separate keys give the same hashCode() value, the performance of the hashMap degrades. | 
| When the original size of HashMap is full, the HashMap require resizing.  The elements from the previous HashMap are transferred to a new bigger HashMap, which takes O(n) time.|
| when iterating through a hashmap, you can not ensure ordering |

### The space and time complexity of its behavior

The get/put/containsKey() operations are O(1) in average case and attein O(n) in the worst cases.

---

## Setting Up the Classes
Below You'll find the skeletons of the classes you'll need for this tutorial

### Key Class
The First Class you'll have to set up is the Key class. This class, true to its name, will serve as the key in our key - value pair.
It is a wrapper class allowing users to choose the type of object they want as keys while allowing us to track specific pieces of data related to it.
 
```java
import org.jetbrains.annotations.NotNull;

public class Key<K> implements Comparable<Key<K>>{ 
    
    private final K key;
    private int mappingIndex;
    private int numItemsMapped;
    
    Key(K key) {}
    
    @Override 
    public int hashCode() {}
    
    public void setMappingIndex(int mappingIndex) {}
        
    public int getMappingIndex() {}
        
    public int getNumItemsMapped() {}
        
    public void incrementItemsMapped() {}
        
    @Override
    public boolean equals(Object o) {}
        
    public K getKey() {}
        
    @Override
    public int compareTo(@NotNull Key<K> otherKey) {}
        
    @Override
    public String toString() {}

}
```

### Node Class
The next class on our list is the Node Class. This class serves as containers for our key - value pairs. They will be used
in our hash map as chains linking key - values pairs to specific indexes while allowing for collision. 
```java
public class Node<V> { 
    
    private final V value;
    private Node<V> nextValue;
    private Key<?> key;
    
    Node(V value) {}
    
    public V getValue() {}
    
    public void setNextValue(Node<V> nextValue) {}
    
    public Node<V> getNextValue() {}
    
    public Key<?> getKeyObject() {}
    
    @Override 
    public boolean equals(Object o) {}
    
    @Override 
    public int hashCode() {}
    
    @Override 
    public String toString() {}
    
    public void setKey(Key<?> key) {}
}
```

### HashMap Class
The final class that we'll have to set up is the HashMap Class. Through Composition this class contains a reference to a 
set of Keys and an array of Nodes. We've already explained how a HashMap generally works but to save you some scroll time
we'll briefly describe it again. Our HashMap accepts objects as either a Keys or Values the type of which is decided 
through the use of Type parameters at instantiation. The Key that the user inputs is then hashed to find an index to store
the pair in. If two Keys end up refering to the same index then the newest pair is "Linked" to the back of the whatever 
was their before it.  
```java
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class HashMap <K extends Comparable<K>,V extends Comparable<V>> implements Map<K,V>{ 
    
    private final int INITIAL_SIZE = 16;
    private final double DEFAULT_LOAD_FACTOR = 0.65;
    private final TreeSet<Key<K>> keys = new TreeSet<>();
    private Node<V>[] values;
    private int filledSlots;
    private double loadFactor = DEFAULT_LOAD_FACTOR;
    
    HashMap() {}

    HashMap(int initialSize) {}

    HashMap(int initialSize, double loadFactor) {}

    HashMap(double loadFactor) {}

    private void initArray(int initSize) {}
    
    @Override
    public int size() {}

    @Override
    public boolean isEmpty() {}

    private void resize() {}

    @Override
    public Set<K> keySet() {}

    @Override
    public boolean containsKey(Object key) {}

    private int getMapping(Key<K> key) {}

    @Override
    public ArrayList<V> values() {}

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {}

    public V put(K key, V value) {}

    private void removeNode(Key<K> key) {}

    public V remove(Object key) {}

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {}

    @Override
    public void clear() {}

    private void insertNode(V value, int index, Key<K> key) {}

    private void addKey(Key<K> key) {}

    public ArrayList<V> values(K key) {}

    @Override
    public V get(Object key) {}

    @Override
    public boolean containsValue(Object value) {}

    public double getCurrentLoad() {}

    public int getFilledSlots() {}

    public double getLoadFactor() {}

    public void setLoadFactor(double loadFactor) {}

    @Override
    public String toString() {}
}
```

---

## Method Implementations

Now we move on to our methods, be warned there are a few of them. 

### Key Constructor

```java
    Key(K key) {
        this.key = key;
    }
```

### Key Methods

#### hashCode

Generates the hashcode for the key. For this example implementation we are keeping it simple and adding up the values 
of the ``.toString()`` characters.

```java
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
```

#### setMappingIndex
Setter for the mappingIndex 

```java
    public void setMappingIndex(int mappingIndex) {
        this.mappingIndex = mappingIndex;
    }
```

#### getMappingIndex

Getter for the currentMapping index. Returns the current location the key points to.

```java
    public int getMappingIndex() {
        return mappingIndex;
    }
```

#### getNumItemsMapped

Getter for the number of items the key points to. Returns number of items the key points to.

```java
    public int getNumItemsMapped() {
        return numItemsMapped;
    }
```

#### incrementItemsMapped

Increments the number of items the key points to.

```java
    public void incrementItemsMapped() {
        numItemsMapped++;
    }
```

#### equals

Compares the current instance of Key to another Object. Returns true if objects are equal, false otherwise.

```java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key<?> key1 = (Key<?>) o;

        if (mappingIndex != key1.mappingIndex) return false;
        return key.equals(key1.key);
    }
```

#### getKey

Getter for the Key Object. Returns the Internal Key object.

```java
    public K getKey() {
        return key;
    }
```

#### compareTo

Takes in a Key object and compares to another Key. Returns an Integer < 0 if other key has higher hashCode, 0 if same, > 0 if lower than.

```java
    @Override
    public int compareTo(@NotNull Key<K> otherKey) {
        return this.hashCode() - otherKey.hashCode();
    }
```

#### toString

String representation of the Key, returns the Stringified version of the object.

```java
    @Override
    public String toString() {
        return "\n\t\tKey{" +
                "key=" + key +
                ", mappingIndex=" + mappingIndex +
                ", numberOfItemMapped=" + numItemsMapped +
                "}";
    }
```
    
### Node Constructor
```java
    Node(V value) {
        this.value = value;
    }
```

### Node Methods

#### getValue

Getter for Values.

```java
    public V getValue() {
        return this.value;
    }
```

#### setNextValue

Sets the next Node in the Link.

```java
    public void setNextValue(Node<V> nextValue) {
        this.nextValue = nextValue;
    }
```

#### getNextValue

Returns the value which follows this one, when they are mapped to the same location. Returns the next that was mapped to
this same index.


```java
    public Node<V> getNextValue() {
        return this.nextValue;
    }
```

#### getKeyObject

Getter for the Node Key Object.
Returns the Key to which this value belongs to.

```java
    public Key<?> getKeyObject() {
        return this.key;
    }
```

#### equals

Compares this object to another object.
Returns true if objects are equals, false otherwise.

```java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?> node = (Node<?>) o;

        return value.equals(node.value);
    }
```    

#### hashCode

Used to overwrite the Object `hashCode()` returns Hashcode for our value.

```java
    @Override
    public int hashCode() {
        return value.hashCode();
    }
```

#### toString

Used to convert Object to string format returns string version of the object.

```java
    @Override
    public String toString() {
        return "\n\t\tNode{" +
                "value=" + value +
                ",nextValue=" + nextValue +
                "}";
    }
```    

#### setKey

Used to set the key this object belongs to.

```java
    public void setKey(Key<?> key) {
        this.key = key;
    }
```

### HashMap Constructors
```java
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
```

### HashMap Methods

#### initArray

Used to initialize the internal array structure which holds the values.

```java
    private void initArray(int initSize) {
        values = (Node<V>[]) new Node<?>[initSize];
    }
```
#### size

Returns the number of filled slots.

```java
    @Override
    public int size() {
        return filledSlots;
    }
```

#### isEmpty

Returns true if no slots have been filled.

```java
    @Override
    public boolean isEmpty() {
        return filledSlots > 0;
    }
```

#### resize

This function is in charge of resizing the internal array which hold the values linkedLists.
We do this by coping our current values into an old array, then resize the new array to be twice the size
it currently is, then we iterate through each node in the old values array and reinsert them into the new values
array.

```java
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
```

#### keySet

Returns all the keys currently in the hash map

```java
    @Override
    public Set<K> keySet() {
        TreeSet<K> returnKeys = new TreeSet<>();
        keys.forEach(key -> returnKeys.add(key.getKey()));
        return returnKeys;
    }
```

#### containsKey

Utility function to check if the current key is already in the hash map.
Returns true if key is present, false otherwise.

```java
    @Override
    public boolean containsKey(Object key) {
        return keys.contains(new Key<>(key));
    }
```

#### getMapping

Function maps the hashcode for they key to an index in the values array.
Returns an Index the Key maps to.

```java
    private int getMapping(Key<K> key) {
        //Does Modulo with Values Length
        return (key.hashCode() % (values.length));
    }
```

#### values

Function returns all the values currently in the hash Map, these values are returned in their order.

```java
    @Override
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
```

#### entrySet

Returns a set containing Key - Value pairs.

```java
    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
       TreeSet<Entry<K,V>> entries = new TreeSet<>();

       keys.forEach((key) -> {
           V keyFirstValue = get(key);
           entries.add(new AbstractMap.SimpleEntry<K,V>(key.getKey(), keyFirstValue));
       });

       return entries;
    }
```

#### put

Function used to add keys and values to the hash map.

```java
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
        addKey(newKey);

        return value;
    }
```

#### removeNode

Utility Function to remove a node from values array.

```java
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
```

#### remove

Function used to remove the key from the Hash Map, this will also remove all the Values associated with that key.
Returns the first value attacked to the key. All items are removed though.

```java
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
    }
```

#### putAll

Adds Keys and Values from a map into the hash

```java
    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }
```

#### clear

Resets the Hash map clearing out all keys and values. 

```java
    @Override
    public void clear() {
        keys.clear();
        values = (Node<V>[]) new Object[INITIAL_SIZE];
    }
```

#### insertNode

Function used when inserting a node into an already existing Node chain.

```java
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
```

#### addKey

Function to add the key to the set, if the key already is in the set, increment the number of values the key points to.

```java
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
```

#### values

Function returns all the values in the values array which are mapped to the passed key.

```java
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
```

#### get

Utility function to return the first value attached to the key.

```java
    @Override
    public V get(Object key) {
        return new LinkedList<>(values((K) key)).getFirst();
    }
```

#### containsValue

Utility Function to check the passed value is currently in the hash map.
Returns true if the value was found, false otherwise.

```java
    @Override
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
```

#### getCurrentLoad

Reads the current Load on the HashMap.
Returns the current load on the hashmap.

```java
    public double getCurrentLoad() {
        return filledSlots / (double) values.length;
    }
```

#### getFilledSlots

Gets the current number of slots filled on the map.
Returns filledSlots.

```java
    public int getFilledSlots() {
        return filledSlots;
    }
```

#### getLoadFactor

Get the current load factor used when deciding when to auto expand the hash map.
Returns the current load factor (value between 0 and 1).

```java
    public double getLoadFactor() {
        return loadFactor;
    }
```

#### setLoadFactor

Updates the current load factor.

```java
    public void setLoadFactor(double loadFactor) {
       if(loadFactor < 1 && loadFactor > 0) {
           this.loadFactor = loadFactor;
       }
    }
```

#### toString

Creates a string version of the Map.
Returns a string representation of the map.

```java
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
```

## Demonstration
Demonstrate the usage of the Data Structure with a few examples


You can build your tutorial with the tool of your choosing so long as you can show text, code, images, etc. We reccomend using a markdown file but a website or Word document will also work.

Presentation
You will give a short presintation to the rest of the class. Here you will explain all about your Data Structure and provide a demonstration of why and how to use it.
