package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node<T> {

    private int[][] data;
    private List<Node<T>> children = new ArrayList<>();
    private Node<T> parent = null;
    private int knucklesNotInPlace;
    private int manhatten;

    public int getManhatten() {
        return manhatten;
    }

    public void setManhatten(int manhatten) {
        this.manhatten = manhatten;
    }

    public int getKnucklesNotInPlace() {
        return knucklesNotInPlace;
    }

    public void setKnucklesNotInPlace(int knucklesNotInPlace) {
        this.knucklesNotInPlace = knucklesNotInPlace;
    }

    public Node(int[][] data) {
        this.data = data;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public int[][] getData() {
        return data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return this.getParent() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return Arrays.deepEquals(data, node.data);
    }

}