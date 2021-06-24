package com.codediff;

/***
 * Class will contain the Values used in the hashmap
 * @param <V>: Datatype of Values
 */
public class Node<V> {
    private final V value;
    private Node<V> nextValue;
    private Key<?> key;

    Node(V value) {
        this.value = value;
    }

    /***
     * Getter for Values
     * @return: Returns the value currently stored in the Node.
     */
    public V getValue() {
        return this.value;
    }

    /**
     * Sets the next Node in the Link
     * @param nextValue: the next Node in the Link.
     */
    public void setNextValue(Node<V> nextValue) {
        this.nextValue = nextValue;
    }

    /***
     * Returns the value which fallows this one, when they are mapped to the same location.
     * @return: Returns the next that was mapped to this same index.
     */
    public Node<V> getNextValue() {
        return this.nextValue;
    }

    /**
     * Getter for the Node Key Object.
     * @return: Returns the Key to which this value belongs to.
     */
    public Key<?> getKeyObject() {
        return this.key;
    }

    /**
     * Compare this object to another object.
     * @param o : Object to compare against.
     * @return: true of objects are equals, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?> node = (Node<?>) o;

        return value.equals(node.value);
    }

    /***
     * Used to overwrite the Object hashCode()
     * @return: Hashcode for our value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Used to convert Object to string format.
     * @return: String version of the object.
     */
    @Override
    public String toString() {
        return "\n\t\tNode{" +
                "value=" + value +
                ",nextValue=" + nextValue +
                "}";
    }

    /**
     * Used to set the key this object belongs to.
     * @param key: parent Key.
     */
    public void setKey(Key<?> key) {
        this.key = key;
    }
}