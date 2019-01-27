/*
 * Jacob Hermann
 * herma655@umn.edu
 * 5370234
 * Project 5, Lab 13
 */

import java.util.Stack;

public class BinaryTree<K extends Comparable<K>, V> {
    private Node<K, V> root;

    /**
     * Constructor that creates a tree with the node provided.
     *
     * @param root  The root Node of the new binary tree.
     */
    public BinaryTree(Node<K, V> root) {
        this.root = root;
    }

    /**
     * Getter method for the root of a tree.
     *
     * @return  The Node acting as the root of the tree.
     */
    public Node<K, V> getRoot() {
        return this.root;
    }

    /**
     * Adds a new Node object to a tree.
     *
     * @param key    The key associated with the new node.
     * @param value  The value associated with the new node.
     */
    public void add(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
        } else {
            Node<K, V> currentNode = traverseTo(key);
            Node<K, V> newNode = new Node<>(key, value);
            if (key.compareTo(currentNode.getKey()) == 0) {
                currentNode.setValue(value);
            } else if (key.compareTo(currentNode.getKey()) < 0) {
                currentNode.setLeft(newNode);
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                currentNode.setRight(newNode);
            }
        }
    }

    /**
     * Finds the value associated with the given key.
     *
     * @param key  The key whose value is being searched for.
     * @return  The value associated with the given key, or null if the key does not exist
     */
    public V find(K key) {
        Node<K, V> currentNode = traverseTo(key);
        if (currentNode != null && key.compareTo(currentNode.getKey()) == 0) {
            return currentNode.getValue();
        } else {
            return null;
        }

    }

    /**
     * Converts the tree into an array of values, sorted by the keys associated with those values.
     *
     * @return  An array of the values in the tree.
     */
    @SuppressWarnings("unchecked")
    public V[] flatten() {
        int index = 0;
        int size = getSize(root);
        V[] valueArray = (V[]) new Object[size];

        Node<K, V> currentNode = root;
        Stack<Node<K, V>> nodeStack = new Stack<>();

        while (index < size) {
            if (hasLeft(currentNode)) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeft();
            } else {
                valueArray[index] = currentNode.getValue();
                index++;
                while (!hasRight(currentNode) && index < size) {
                    currentNode = nodeStack.pop();
                    valueArray[index] = currentNode.getValue();
                    index++;
                }
                if (hasRight(currentNode)) {
                    currentNode = currentNode.getRight();
                }
            }
        }

        return valueArray;
    }

    /**
     * Removes the Node with the given key from the tree, if it exists.
     *
     * @param key  The key whose Node will be removed from the tree.
     */
    public void remove(K key) {
        Node<K, V> previousNode = null;
        int pastDirection = -1;
        Node<K, V> currentNode = root;
        boolean cont = true;
        while (cont) {
            if (key.compareTo(currentNode.getKey()) < 0) {
                if (hasLeft(currentNode)) {
                    previousNode = currentNode;
                    pastDirection = 0;
                    currentNode = currentNode.getLeft();
                } else {
                    cont = false;
                }
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                if (hasRight(currentNode)) {
                    previousNode = currentNode;
                    pastDirection = 1;
                    currentNode = currentNode.getRight();
                } else {
                    cont = false;
                }
            } else if (key.compareTo(currentNode.getKey()) == 0) {
                // found the Node of interest

                // no subtree
                if (!hasLeft(currentNode) && !hasRight(currentNode)) {
                    if (pastDirection == -1) {
                        // must be root of tree
                        root = null;
                    } else if (pastDirection == 0) {
                        previousNode.setLeft(null);
                    } else {
                        previousNode.setRight(null);
                    }
                }

                // left subtree
                else if (hasLeft(currentNode) && !hasRight(currentNode)) {
                    Node<K, V> newNext = currentNode.getLeft();
                    if (pastDirection == -1) {
                        // must be root of tree
                        root = newNext;
                    } else if (pastDirection == 0) {
                        previousNode.setLeft(newNext);
                    } else {
                        previousNode.setRight(newNext);
                    }
                }

                // right subtree
                else if (!hasLeft(currentNode) && hasRight(currentNode)) {
                    Node<K, V> newNext = currentNode.getRight();
                    if (pastDirection == -1) {
                        // must be root of tree
                        root = newNext;
                    } else if (pastDirection == 0) {
                        previousNode.setLeft(newNext);
                    } else {
                        previousNode.setRight(newNext);
                    }
                }

                // both subtrees
                else if (hasLeft(currentNode) && hasRight(currentNode)) {
                    Node<K, V> newNext = currentNode.getLeft();
                    while (hasRight(newNext)) {
                        newNext = newNext.getRight();
                    }
                    remove(newNext.getKey());
                    if (pastDirection == -1) {
                        // must be root of tree
                        root = newNext;
                    } else if (pastDirection == 0) {
                        previousNode.setLeft(newNext);
                    } else {
                        previousNode.setRight(newNext);
                    }

                    newNext.setRight(currentNode.getRight());
                    if (hasLeft(currentNode)) {
                        newNext.setLeft(currentNode.getLeft());
                    }
                }

                // decrement the size of the tree by 1
                cont = false;
            }
        }
    }

    /**
     * Determines whether or not the tree contains a given subtree.
     *
     * @param other  The subtree being compared to the tree.
     * @return  Boolean true if the tree contains the given subtree, or false if the tree does not.
     */
    public boolean containsSubtree(BinaryTree<K, V> other) {
        if (other.getRoot() == null) {
            return true;
        }

        Node<K, V> otherRoot = other.getRoot();
        Node<K, V> thisSubRoot = traverseTo(otherRoot.getKey());
        if (thisSubRoot != null) {
            return compareRoots(thisSubRoot, otherRoot);
        } else {
            return false;
        }

    }

    /**
     * Helper method to find the number of nodes within the tree.
     *
     * @param root  The Node acting as the root of the tree.
     * @return  The number of (non-null) Nodes in the tree.
     */
    private int getSize(Node<K, V> root) {
        if (root == null) {
            return 0;
        }
        int size = 1;

        if (hasLeft(root) && hasRight(root)) {
            size += (getSize(root.getLeft()) + getSize(root.getRight()));
        } else if (hasLeft(root)) {
            size += getSize(root.getLeft());
        } else if (hasRight(root)) {
            size += getSize(root.getRight());
        }

        return size;
    }

    /**
     * Helper method to traverse the tree to find the Node with the given key.
     *
     * @param key  The key of the Node that is being searched for.
     * @return  The Node with the given key.
     */
    private Node<K, V> traverseTo(K key) {
        Node<K, V> currentNode = root;
        boolean cont = true;
        while (cont) {
            if (key.compareTo(currentNode.getKey()) < 0) {
                if (hasLeft(currentNode)) {
                    currentNode = currentNode.getLeft();
                } else {
                    cont = false;
                }
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                if (hasRight(currentNode)) {
                    currentNode = currentNode.getRight();
                } else {
                    cont = false;
                }
            } else if (key.compareTo(currentNode.getKey()) == 0) {
                return currentNode;
            }
        }
        return currentNode;
    }

    /**
     * Helper method for comparing subtrees of two trees.
     *
     * @param root   The root of the first subtree being compared.
     * @param other  The root of the other subtree being compared.
     * @return  Boolean true if the entire structure below these two roots are the same, false if not.
     */
    private boolean compareRoots(Node<K, V> root, Node<K, V> other) {
        if (root == null && other == null) {
            return true;
        } else if (root.equals(other)) {
            boolean left = compareRoots(root.getLeft(), other.getLeft());
            boolean right = compareRoots(root.getRight(), other.getRight());
            return (left && right);
        } else {
            return false;
        }
    }

    /**
     * Helper method to determine if the given Node has a left Node.
     *
     * @param node  The Node being analyzed.
     * @return  Boolean true if the Node does have a left Node, false if not.
     */
    private boolean hasLeft(Node<K, V> node) {
        return (node.getLeft() != null);
    }

    /**
     * Helper method to determine if the given Node has a right Node.
     *
     * @param node  The Node being analyzed.
     * @return  Boolean true if the Node does have a left Node, false if not.
     */
    private boolean hasRight(Node<K, V> node) {
        return (node.getRight() != null);
    }

}