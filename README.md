Java Data Structure Tutorial Project

# HashMap

* **How it works/stores data** 

    - A Hashmap is a data structure that implementates the interface Map of Java. It stores data using the Key and Value relationship, each Key is paired to a unique value. To access both of them you can use the index of the another.
You can not have duplicated key in the same hashmap but dupcated values are allowed.

* **How a client interacts with it (interface)**

   - To acces an elements from the hashmap the client can use the key to store and retreive a value. They also have the option to get all the keys and all the values in that hashmap.
   
* **The pros and cons of using the data structure**

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

* **The space and time complexity of its behavior**

    - The get/put/containsKey() operations are O(1) in average case and attein O(n) in the worst cases.
   

##Setting Up the Classes

Below You'll find the skeletons of the classes you'll need for this tutorial

###HashMap Class
```java 
public class HashMap <K extends Comparable<K>,V extends Comparable<V>> {

    private final int INITIAL_SIZE = 16;
    private final double DEFAULT_LOAD_FACTOR = 0.65;
    private final TreeSet<Key<K>> keys = new TreeSet<>();
    private Node<V>[] values;
    private int filledSlots;
    private double loadFactor= DEFAULT_LOAD_FACTOR;

    HashMap() {}

    HashMap(int initialSize) {}
    
    HashMap(int initialSize, double loadFactor) {}
    
    HashMap(double loadFactor) {}

    private void initArray(int initSize) {}

    private void resize() {}

    public ArrayList<Key<K>> getKeys() {}

    public boolean containsKey(K key) {}

    private int getMapping(Key<K> key) {}

    private int getNewMapping(Key<K> key, int newSize) {}

    public ArrayList<V> getValues() {}

    public void put(K key, V value) {}

    private void removeNode(Key<K> key) {}

    public void remove(K key) {}

    private void insertNode(V value, int index, Key<K> key) {}

    private void addKey(Key<K> key) {}

    public ArrayList<V> getValues(K key) {}

    public boolean containsValue(V value) {}

    public double getCurrentLoad() {}

    public int getFilledSlots() {}

    public double getLoadFactor() {}

    public void setLoadFactor(double loadFactor) {}

    @Override
    public String toString() {}
}
```

###Key Class
```java
public class Key<K> implements Comparable<Key<K>>{
    
    private K key;
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

###Node Class
```java
public class Node<V> {
    
    private V value;
    private Node<V> nextValue;
    private Key key;

    Node(V value) {}

    public V getValue() {}

    public void setNextValue(Node<V> nextValue) {}

    public Node<V> getNextValue() {}

    public Key getKeyObject() {}

    @Override
    public boolean equals(Object o) {}

    @Override
    public int hashCode() {}

    @Override
    public String toString() {}

    public <K extends Comparable<K>> void setKey(Key<K> key) {}
}
```

##Method Implementations

One method at a time, provide a decription and implement.
```java
// Pack allows us to add more things into the box so long as the box is not currently stored
public void pack(T t){
    if(!this.isStored){
        this.stuff.add(t);
    }
}
```

##Demonstration
Demonstrate the usage of the Data Structure with a few examples

```java
Box<String> summerClothes = new Box<>();
summerClothes.pack("Swimming Trunks");
summerClothes.pack("Bucket Hat");
summerClothes.store();
summerClothes.pack("Flippy Floppies"); // Can't add because the box has already been stored!!!
```

You can build your tutorial with the tool of your choosing so long as you can show text, code, images, etc. We reccomend using a markdown file but a website or Word document will also work.

Presentation
You will give a short presintation to the rest of the class. Here you will explain all about your Data Structure and provide a demonstration of why and how to use it.
