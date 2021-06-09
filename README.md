Java Data Structure Tutorial Project
You will be given a Data Structure to research. You goal is to create a tutorial for that data structure. For a reference, checkout FreeCodeCamp.

Step 1: Research
Research about your Data Structure. You should know:

* How it works/stores data
* How a client interacts with it (interface)
* The pros and cons of using the data structure
* The space and time complexity of its behavior
# HashMap

* How it works/stores data 
    * A Hashmap is a data structure that implementates the interface Map of Java. It stores data using the Key and Value relationship, each Key is paired to a unique value. To access both of them you can use the index of the another.
You can not have duplicated key in the same hashmap but dupcated values are allowed.
* How a client interacts with it (interface)
* The pros and cons of using the data structure

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

  
* The space and time complexity of its behavior
   - 

**Tutorial**
Write a turotial that another developer could follow. We recommend the following structure:

Show how to set up the required classes and stub all the method

```java 
class Box<T> {

    private List<T> stuff;
    private boolean isStored;

    public void pack(T t){}
    public T[] unpack(){return null;}
    public void store(){}
    public void unstore(){}

}
```

One method at a time, provide a decription and implement.
```java
// Pack allows us to add more things into the box so long as the box is not currently stored
public void pack(T t){
    if(!this.isStored){
        this.stuff.add(t);
    }
}
```

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
