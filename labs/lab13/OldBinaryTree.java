/**
 * Jacob Hermann, herma655@umn.edu
 * Project 5
 *
 * @param <K>  The object type of the keys used to create Node objects
 * @param <V>  The object type of the values used to create Node objects
 */

public class OldBinaryTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int size = 0;


    /**
     * Constructor method that initiates a tree using a given root node.
     *
     * @param root  A node that will be used as the root of the tree.
     */
    public OldBinaryTree(Node<K,V> root) {
        this.root = root;
        size++;
    }


    /**
     * Retrieves the root Node of the tree.
     *
     * @return  The root Node of the tree.
     */
    public Node<K, V> getRoot() {
        return this.root;
    }


    /**
     * Adds a new Node object to the existing tree in the correct location (ordered by key value).
     *
     * @param key    The key that will be assigned to the new node.
     * @param value  The value that will be assigned to the new node.
     */
    public void add(K key, V value) {
        if (root == null) {
            root = new Node<>(key,value);
        } else if (find(key) != null) {
            remove(key);
            add(key,value);
        } else {
            if (key.compareTo(root.getKey()) < 0) {
                addLeft(key, value);
                size++;
            } else if (key.compareTo(root.getKey()) > 0) {
                addRight(key, value);
                size++;
            }
        }
    }


    /**
     * Helper method that traverses the tree to the left and adds a new Node in the correct location.
     *
     * @param key    The key that will be assigned to the new node.
     * @param value  The value that will be assigned to the new node.
     */
    private void addLeft(K key, V value) {
        Node<K,V> newNode = new Node<>(key,value);
        Node<K,V> treeNode = root;
        boolean hasLeft = hasLeft(treeNode);
        while (hasLeft && key.compareTo(treeNode.getLeft().getKey())<0 ) {
            treeNode = treeNode.getLeft();
            hasLeft = hasLeft(treeNode);
        }
        if (! hasLeft) {
            treeNode.setLeft(newNode);
            newNode.setRight(treeNode);
        } else {
            newNode.setLeft(treeNode.getLeft());
            newNode.setRight(treeNode);
            treeNode.getLeft().setRight(newNode);
            treeNode.setLeft(newNode);
        }
    }


    /**
     * Helper node to determine if there is a Node to the left of the current Node.
     *
     * @param treeNode  The current Node that the tree has iterated to.
     * @return  The boolean true if there is a Node to the left or false if the current Node is the last Node.
     */
    private boolean hasLeft(Node treeNode) { return !(treeNode.getLeft()==null); }


    /**
     * Helper method that traverses the tree to the right and adds a new Node in the correct location.
     *
     * @param key    The key that will be assigned to the new node.
     * @param value  The value that will be assigned to the new node.
     */
    private void addRight(K key, V value) {
        Node<K,V> newNode = new Node<>(key,value);
        Node<K,V> treeNode = root;
        boolean hasRight = hasRight(treeNode);
        while (hasRight && key.compareTo(treeNode.getRight().getKey())>0 ) {
            treeNode = treeNode.getRight();
            hasRight = hasRight(treeNode);
        }
        if (! hasRight) {
            treeNode.setRight(newNode);
            newNode.setLeft(treeNode);
        } else {
            newNode.setRight(treeNode.getRight());
            newNode.setLeft(treeNode);
            treeNode.getRight().setLeft(newNode);
            treeNode.setRight(newNode);

        }
    }


    /**
     * Helper node to determine if there is a Node to the right of the current Node.
     *
     * @param treeNode  The current Node that the tree has iterated to.
     * @return  The boolean true if there is a Node to the right or false if the current Node is the last Node.
     */
    private boolean hasRight(Node treeNode) { return !(treeNode.getRight()==null); }


    /**
     * Searches the tree to determine if a given key is present.
     *
     * @param key  The key that is being searched for.
     * @return  The value associated with the given key if the key exists, or null if it doesn't exist.
     */
    public V find(K key) {
        V value;
        if (key.equals(root.getKey())) {
            value = root.getValue();
        } else if (key.compareTo(root.getKey()) < 0) {
            value = findLeft(key);
        } else {
            value = findRight(key);
        }
        return value;
    }


    /**
     * Helper method that searches the tree to the left to find a given key.
     *
     * @param key  The key that is being searched for.
     * @return  The value associated with the given key if the key exists, or null if it doesn't exist.
     */
    private V findLeft(K key) {
        Node<K,V> treeNode = root;
        boolean hasLeft = hasLeft(treeNode);
        while (hasLeft && key.compareTo(treeNode.getLeft().getKey())<=0 ) {
            treeNode = treeNode.getLeft();
            hasLeft = hasLeft(treeNode);
        }
        if (key.compareTo(treeNode.getKey()) == 0) {
            return treeNode.getValue();
        } else {
            return null;
        }
    }


    /**
     * Helper method that searches the tree to the right to find a given key.
     *
     * @param key  The key that is being searched for.
     * @return  The value associated with the given key if the key exists, or null if it doesn't exist.
     */
    private V findRight(K key) {
        Node<K,V> treeNode = root;
        boolean hasRight = hasRight(treeNode);
        while (hasRight && key.compareTo(treeNode.getRight().getKey())<=0 ) {
            treeNode = treeNode.getRight();
            hasRight = hasRight(treeNode);
        }
        if (key.compareTo(treeNode.getKey()) == 0) {
            return treeNode.getValue();
        } else {
            return null;
        }
    }


    /**
     * Converts the tree into an array.
     *
     * @return  An array of the tree's values, in the same order they are in the tree.
     */
    @SuppressWarnings("unchecked")
    public V[] flatten() {
        V[] treeArray = (V[]) new Object[size];
        Node<K,V> treeNode = root;

        //traverse to smallest key
        boolean hasLeft = hasLeft(treeNode);
        while (hasLeft) {
            treeNode = treeNode.getLeft();
            hasLeft = hasLeft(treeNode);
        }

        //traverse entire tree
        boolean hasRight = hasRight(treeNode);
        int index = 0;
        treeArray[index] = treeNode.getValue();
        while (hasRight) {
            index++;
            treeNode = treeNode.getRight();
            treeArray[index] = treeNode.getValue();
            hasRight = hasRight(treeNode);
        }

        return treeArray;
    }


    /**
     * Searches the tree for a given key and removes the Node of that tree, if it exists. If the Node is the root of
     *    the tree, the entire tree is
     *
     * @param key  The key that will be removed, if it exists.
     */
    public void remove(K key) {
        boolean isNode = false;

        if (key.equals(root.getKey())) {
            root = null;
            size = 0;
        } else if (key.compareTo(root.getKey()) < 0) {
            isNode = removeLeft(key);
        } else {
            isNode = removeRight(key);
        }

        if (isNode) { size--; }
    }

    /**
     *
     * @param key  The key that will be assigned to the new node.
     * @return
     */
    private boolean removeLeft(K key) {
        Node<K,V> treeNode = root;
        boolean hasLeft = hasLeft(treeNode);
        while (hasLeft && key.compareTo(treeNode.getLeft().getKey())<=0 ) {
            treeNode = treeNode.getLeft();
            hasLeft = hasLeft(treeNode);
        }
        if (key.compareTo(treeNode.getKey()) == 0) {
            remove(treeNode);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param key  The key that will be assigned to the new node.
     * @return
     */
    private boolean removeRight(K key) {
        Node<K,V> treeNode = root;
        boolean hasRight = hasRight(treeNode);
        while (hasRight && key.compareTo(treeNode.getRight().getKey())>=0 ) {
            treeNode = treeNode.getRight();
            hasRight = hasRight(treeNode);
        }
        if (key.compareTo(treeNode.getKey()) == 0) {
            remove(treeNode);
            return true;
        } else {
            return false;
        }
    }

    private void remove(Node<K,V> node) {
        Node<K,V> leftNode = node.getLeft();
        Node<K,V> rightNode = node.getRight();
        leftNode.setRight(rightNode);
        rightNode.setLeft(leftNode);
    }

    public boolean containsSubtree(OldBinaryTree<K, V> other) {
        if (other.getRoot() == null) {
            return true;
        } else {
            boolean foundMistake = true;
            V[] otherTreeArray = other.flatten();
            V[] thisTreeArray = this.flatten();

            int index = -1;
            for (int i = 0; i < thisTreeArray.length; i++) {
                if (otherTreeArray[0].equals(thisTreeArray[i])) {
                    index = i;
                    foundMistake = false;
                    break;

                }
            }

            int j = 1;
            while (!foundMistake && j<otherTreeArray.length) {
                if (otherTreeArray[j].equals(thisTreeArray[index+j])) {
                    j++;
                } else {
                    foundMistake = true;
                }
            }

            return (!foundMistake);
        }
    }

    public static void main(String[] args) {
        Node<Integer,Integer> root = new Node<>(10,0);
        OldBinaryTree<Integer,Integer> tree = new OldBinaryTree<>(root);
        tree.add(9,-1);
        tree.add(7,-3);
        tree.add(6,-4);
        tree.add(8,-2);
        tree.add(12,2);
        tree.add(11,1);
        tree.add(14,4);
        tree.add(13,3);

        Object[] treeArray = tree.flatten();
        for (Object val : treeArray) {
            System.out.print(val + "\t");
        }
        System.out.println();


        tree.remove(7);
        tree.remove(9);
        tree.remove(11);
        tree.remove(13);

        treeArray = tree.flatten();
        for (Object val : treeArray) {
            System.out.print(val + "\t");
        }
        System.out.println();


        Node<Integer,Integer> newRoot = new Node<>(10,0);
        OldBinaryTree<Integer,Integer> subTree = new OldBinaryTree<>(newRoot);
        subTree.add(9,-1);
        subTree.add(11,1);
        subTree.add(8,-2);
        subTree.add(7,-3);

        System.out.println(tree.containsSubtree(subTree));


    }


}
