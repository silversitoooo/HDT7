package org.example;

import java.util.List;

/**
 * Generic Binary Search Tree implementation
 */
public class BST<E extends Comparable<E>> {
    private Node<E> root;

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public BST() {
        this.root = null;
    }

    // Insert an element
    public void insert(E data) {
        root = insertRec(root, data);
    }

    private Node<E> insertRec(Node<E> root, E data) {
        // If the tree is empty, return a new node
        if (root == null) {
            return new Node<>(data);
        }

        // Otherwise, recur down the tree
        int compareResult = data.compareTo(root.data);

        if (compareResult < 0) {
            root.left = insertRec(root.left, data);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, data);
        }
        // If data already exists, we don't insert (no duplicates)

        return root;
    }

    // Search for an element
    public E search(E data) {
        Node<E> result = searchRec(root, data);
        return result != null ? result.data : null;
    }

    private Node<E> searchRec(Node<E> root, E data) {
        // Base cases: root is null or data is at root
        if (root == null || data.compareTo(root.data) == 0) {
            return root;
        }

        // Data is greater than root's data
        if (data.compareTo(root.data) > 0) {
            return searchRec(root.right, data);
        }

        // Data is less than root's data
        return searchRec(root.left, data);
    }

    // In-order traversal to get sorted elements
    public void inOrderTraversal(List<E> result) {
        inOrderTraversalRec(root, result);
    }

    private void inOrderTraversalRec(Node<E> root, List<E> result) {
        if (root != null) {
            inOrderTraversalRec(root.left, result);
            result.add(root.data);
            inOrderTraversalRec(root.right, result);
        }
    }
}