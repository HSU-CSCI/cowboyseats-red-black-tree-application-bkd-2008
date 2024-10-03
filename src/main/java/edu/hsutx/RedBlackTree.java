package edu.hsutx;

/**
 * @author Todd Dole
 * @version 1.0
 * Starting Code for the CSCI-3323 Red-Black Tree assignment
 * Students must complete the TODOs and get the tests to pass
 */

/**
 * A Red-Black Tree that takes int key and String value for each node.
 * Follows the properties of a Red-Black Tree:
 * 1. Every node is either red or black.
 * 2. The root is always black.
 * 3. Every leaf (NIL node) is black.
 * 4. If a node is red, then both its children are black.
 * 5. For each node, all simple paths from the node to descendant leaves have the same number of black nodes.
 */
public class RedBlackTree<E> {
    Node root;
    int size;

    protected class Node {
        public String key;
        public E value;
        public Node left;
        public Node right;
        public Node parent;
        public boolean isRed; // true = red, false = black

        /**
         * Method to create an existing node with no key
         */
        public Node(Node parent) {
            this.key = null;
            this.value = null;
            this.parent = parent;
            this.left = null;
            this.right = null;
            this.isRed = false;     //is a leaf, so is always black
        }

        public Node(String key, E value, Node parent, boolean color) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = new Node(this);
            this.right = new Node(this);
            this.isRed = color;
        }

        // TODO - add comments as appropriate including a javadoc for each method
        public int getDepth() {
            // TODO - calculate the depth of the node and return an int value.
            // Hint: follow parent pointers up to the root and count steps
            int depth = 1;  //start including the node being checked
            Node current = this.parent;
            while (current != null) {
                depth++;
                current = current.parent;
            }
            return depth;
        }

        public int getBlackDepth() {
            // TODO - calculate the depth of the node counting only black nodes and return an int value
            int depth = 1;  //start including the node being checked
            Node current = this.parent;
            while (current != null) {
                if(!current.isRed) {
                    depth++;
                }
                current = current.parent;
            }
            return depth;
        }

        public boolean isRightChild(Node n) {
            return n.right == this;
        }
    }

    public RedBlackTree() {
        root = null; // Start with an empty tree.  This is the one time we can have a null ptr instead of a null key node
        size = 0;
    }

    public void insert(String key, E value) {
        if (root == null) {
            root = new Node(key, value, null, false);   //empty case, root must be black
            size++;
            return;
        }
        Node newNode = find(key);
        if (newNode.key != null) {
            return;     //a node with this key already exists
        }

        newNode = new Node(key, value, newNode.parent, true);
        if (newNode.key.compareTo(newNode.parent.key) < 0) {
            newNode.parent.left = newNode;
        } else {
            newNode.parent.right = newNode;
        }
        size++;
        fixInsertion(newNode);
    }

    public void delete(String key) {
        // TODO - Implement deletion for a Red-Black Tree
        // Will need to handle three cases similar to the Binary Search Tree
        // 1. Node to be deleted has no children
        // 2. Node to be deleted has one child
        // 3. Node to be deleted has two children
        // Additionally, you must handle rebalancing after deletion to restore Red-Black Tree properties
        // make sure to subtract one from size if node is successfully added
    }

    private void fixInsertion(Node node) {
        // TODO - Implement the fix-up procedure after insertion
        // Ensure that Red-Black Tree properties are maintained (recoloring and rotations).
        // You must handle rebalancing the tree after inserting
        // Recolor and rotate to restore Red-Black Tree properties.
        // Hint: You will need to deal with red-red parent-child conflicts
        if (node == root) {
            node.isRed = false;
            return;
        }
        if (!node.parent.isRed) {
            return;     //no color conflicts
        }

        Node parent = node.parent;
        Node uncle = getUncle(node);
        Node grandparent = uncle.parent;
        if (uncle.isRed) {
            node.parent.isRed = false;
            uncle.isRed = false;
            grandparent.isRed = true;
            fixInsertion(grandparent);
            return;
        }

        if (node.isRightChild(parent) && parent.isRightChild(grandparent)) {
            rotateLeft(node);
        } else if (node.isRightChild(parent) && !parent.isRightChild(grandparent)) {
            rotateLeft(node.right);
            rotateRight(node);
        } else if (parent.isRightChild(grandparent)) {
            rotateRight(node.left);
            rotateLeft(node);
        } else {
            rotateRight(node);
        }
    }

    private void fixDeletion(Node node) {
        // TODO - Implement the fix-up procedure after deletion
        // Ensure that Red-Black Tree properties are maintained (recoloring and rotations).
    }

    private void rotateLeft(Node node) {
        // TODO - Implement left rotation
        // Left rotation is used to restore balance after insertion or deletion
        Node parent = node.parent;
        Node grandparent = parent.parent;
        Node sibling;


        //moves the parent to the grandparent's position
        if (grandparent == root) {
            root = parent;
        } else {
            if (grandparent.isRightChild(grandparent.parent)) {
                grandparent.parent.right = parent;
            } else {
                grandparent.parent.left = parent;
            }
        }
        parent.parent = grandparent.parent;
        grandparent.parent = parent;


        //moves the grandparent to the parent's child
        if (node.isRightChild(parent)) {
            sibling = parent.left;
            parent.left = grandparent;
        } else {
            sibling = parent.right;
            parent.right = grandparent;
        }

        //moves the sibling to the grandparent's child, should always be right child for left rotation
        grandparent.right = sibling;

        parent.isRed = false;
        grandparent.isRed = true;
    }

    private void rotateRight(Node node) {
        // TODO - Implement right rotation
        // Right rotation is used to restore balance after insertion or deletion
        Node parent = node.parent;
        Node grandparent = parent.parent;
        Node sibling;

        //moves parent to grandparent position
        if (root == grandparent) {
            root = parent;
        } else {
            if (grandparent.isRightChild(grandparent.parent)) {
                grandparent.parent.right = parent;
            } else {
                grandparent.parent.left = parent;
            }
        }
        parent.parent = grandparent.parent;
        grandparent.parent = parent;

//        moves grandparent to parent's child
        if (node.isRightChild(parent)) {
            sibling = parent.left;
            parent.left = grandparent;
        } else {
            sibling = parent.right;
            parent.right = grandparent;
        }

        grandparent.left = sibling;

        parent.isRed = false;
        grandparent.isRed = true;
    }

    Node find(String key) {
        // TODO - Search for the node with the given key
        // If the key exists in the tree, return the Node where it is located
        // Otherwise, return a node with a null key
        Node current = root;
        while (current.key != null && current.key != key) {
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    public E getValue(String key) {
        // TODO - Use find() to locate the node with the given key and return its value
        // If the key does not exist, return null
        Node getNode = find(key);
        if (getNode.key == null) {
            return null;
        }
        return getNode.value;
    }

    private Node getUncle(Node n) {
        Node grandparent = n.parent.parent;
        if (grandparent.left == n.parent) {
            return grandparent.right;
        } else {
            return grandparent.left;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    // returns the depth of the node with key, or 0 if it doesn't exist
    public int getDepth(String key) {
        Node node = find(key);
        if (node != null) return node.getDepth();
        return 0;
    }

    // Helper methods to check the color of a node
    private boolean isRed(Node node) {
        return node != null && node.isRed == true; // Red is true
    }

    private boolean isBlack(Node node) {
        return node == null || node.isRed == false; // Black is false, and null nodes are black
    }
    public int getSize() {
        return size;
    }

    // Do not alter this method
    public boolean validateRedBlackTree() {
        // Rule 2: Root must be black
        if (root == null) {
            return true; // An empty tree is trivially a valid Red-Black Tree
        }
        if (isRed(root)) {
            return false; // Root must be black
        }

        // Start recursive check from the root
        return validateNode(root, 0, -1);
    }

    // Do not alter this method
    // Helper method to check if the current node maintains Red-Black properties
    private boolean validateNode(Node node, int blackCount, int expectedBlackCount) {
        // Rule 3: Null nodes (leaves) are black
        if (node == null) {
            if (expectedBlackCount == -1) {
                expectedBlackCount = blackCount; // Set the black count for the first path
            }
            return blackCount == expectedBlackCount; // Ensure every path has the same black count
        }

        // Rule 1: Node is either red or black (implicit since we use a boolean color field)

        // Rule 4: If a node is red, its children must be black
        if (isRed(node)) {
            if (isRed(node.left) || isRed(node.right)) {
                return false; // Red node cannot have red children
            }
        } else {
            blackCount++; // Increment black node count on this path
        }

        // Recurse on left and right subtrees, ensuring they maintain the Red-Black properties
        return validateNode(node.left, blackCount, expectedBlackCount) &&
                validateNode(node.right, blackCount, expectedBlackCount);
    }
}
