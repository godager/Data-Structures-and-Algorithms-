import static java.lang.Integer.min;

//AVL binary search tree
public class BinarySearchTree {

    public Node root;

    public BinarySearchTree (int rootValue, int height) {
        root = new Node(rootValue);
    }

    class Node {
        protected Node left = null;
        protected Node right = null;
        protected int element;
        protected int height;

        public Node(int x) {
            element = x;
        }
    }

    public int height(Node n) {
        if (n == null) return -1;
        return n.height;
    }

    public Node leftRotate(Node z) {
        Node y = z.right;
        Node t1 = z.left;

        //Rotate:
        y.left = z;
        z.right = t1;

        //Update height:
        z.height = 1 + max(height(z.left), height(z.right));
        y.height = 1 + max(height(y.left), height(y.right));

        return y;
    }

    public Node rightRotate(Node z) {
        Node y = z.left;
        Node t2 = z.right;

        //Rotate:
        y.right = z;
        z.left = t2;

        //Update height:
        z.height = 1 + max(height(z.left), height(z.right));
        y.height = 1 + max(height(y.left), height(y.right));

        return y;
    }

    //How unbalanced the tree is:
    public int balanceFactor (Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);

        //Positive: left heavy
    }
    public Node balance (Node n) {
        if (balanceFactor(n) < -1) {
            //-->Right heavy, must rotate left

            //Which case is it?:
            if (balanceFactor(n.right) > 0) {
                n.right = rightRotate(n.right);
            }
            return leftRotate(n);
        }
        if (balanceFactor(n) > 1) {
            if (balanceFactor(n.left) < 0) {
                n.left = leftRotate(n.left);
            }
            return rightRotate(n);
        }
        return n;
    }

    private int max(int height1, int height2) {
        if (height1 < height2) return height2;
        return height1;
    }

    public Node insert (Node n, int x) {
        //Base case. Inserts Node:
        if (n == null)     n = new Node(x);

        //Traverses to the correct free leaf spot:
        else if (x < n.element) n.left = insert(n.left, x);
        else if (x > n.element) n.right = insert(n.right, x);

        //Set height:
        n.height = 1 + max(height(n.left), height(n.right));

        //Balance if unbalanced:
        return balance(n);
    }

    public Node search (Node n, int x) {
        //Base cases:
        if (n == null)      return null;
        if (n.element == x) return n;

        //Searching left or right based on element size:
        if (x < n.element)  return search(n.left, x);
        return search(n.right, x);
    }

    public Node findMin (Node n) {
        if (n.left != null) n = findMin (n.left);
        return n;
    }
    public Node findMax (Node n) {
        if (n.right != null) n = findMax (n.right);
        return n;
    }

    public int shortest(Node n) {
        //Base cases:
        if (n == null)      return 0;
        return min(shortest(n.left), shortest(n.right)) + 1;
    }


    public int findHeight (Node n) {
        //Empty tree:
        if (n == null) return -1;

        //Find deepest child node:
        int height = 0;
        int leftHeight = 0;
        int rightHeight = 0;

        if (n.left != null) leftHeight = findHeight(n.left);
        if (n.right != null) rightHeight = findHeight(n.right);

        height = max(leftHeight, rightHeight);

        //Count:
        return ++height;
    }

    public Node remove (Node n, int x) {
        //Empty tree:
        if (n == null) return n;

        //Recursive search:
        if (x < n.element) {
            n.left = remove (n.left, x);
            return n;
        }
        if (x > n.element) {
            n.right = remove (n.right, x);
            return n;
        }
        //If element is not greater or less we have arrived at the node we want to remove
        //-->
        //0 or 1 children: Remove and plug gap:
        if (n.left == null) {
            return n.right;
        }
        if (n.right == null) {
            return n.left;
        }
        //2 children: Remove and plug gap:
        Node u = findMin(n.right);
        n.element = u.element;

        //Removes old Node:
        n.right = remove (n.right, u.element);

        //Update height and balance:
        n.height = 1 + max(height(n.left), height(n.right));
        return balance(n);
    }

    private Node mirror (Node v) {
        if (v == null) return null;

        mirror(v.left);
        mirror(v.right);

        Node tmp = v.left;
        v.left = v.right;
        v.right = v.left;

        return v;
    }

    public void printInOrder (Node n) {
        if (n == null) return;
        //Left node prints before right in each subtree:
        printInOrder(n.left);
        System.out.println(n.element);
        printInOrder(n.right);
    }

    public Node getRoot() {
        return root;
    }
}

//Some test runs
class BinaryTreeMain {
    public static void main (String[]args) {
        BinarySearchTree bSTree = new BinarySearchTree(8, 4);
        BinarySearchTree.Node r = bSTree.getRoot();
        bSTree.insert(r,3);
        bSTree.insert(r,10);
        bSTree.insert(r,14);
        bSTree.insert(r,1);
        bSTree.insert(r,6);
        bSTree.insert(r,4);
        bSTree.insert(r,7);
        bSTree.insert(r,13);

        bSTree.printInOrder(r);
        bSTree.remove(r,6);
        bSTree.printInOrder(r);
        System.out.println("Max: " + bSTree.findMax(r).element);
        System.out.println("Min: " + bSTree.findMin(r).element);
        System.out.println("7 = " + bSTree.search(r, 7).element);

        BinarySearchTree.Node x = bSTree.getRoot();
        System.out.println("Height = " + bSTree.findHeight(x));
    }
}


