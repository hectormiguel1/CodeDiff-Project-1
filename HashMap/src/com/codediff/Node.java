package com.codediff;
import com.codediff.Key;
public class Node<V> {
    V value;
    Node<V> nextValue;
    Key key;

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

    public Key getKey() {
        return this.key;
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
        return "\nNode{" +
                "value=" + value +
                ",nextValue=" + nextValue +
                "}";
    }

    public <K extends Comparable<K>> void setKey(Key<K> key) {
        this.key = key;
    }
}