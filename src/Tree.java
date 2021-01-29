import java.util.ArrayList;

//Tree structure. Each node may have infinite number of children
public class Tree<T> {

    protected Node root = new Node(null);

    class Node {
        protected Node parent = null;
        protected ArrayList<Node> children = new ArrayList<Node>();
        protected T element;
        public Node (T x) {
            element = x;
        }

        public void addChild (T child) {
            Node newChild = new Node(child);
            newChild.parent = this;
            newChild.children = this.children;
            this.children.add(newChild);
        }
    }

    public void add(T newChild, T parentElement, Node curNode) {

        //Base case
        if (curNode.children.size() == 0) {
            curNode.addChild(newChild);
        }
        else if (curNode.element!=null && curNode.element == parentElement){
            curNode.addChild(newChild);
        }

        //Recursion
        else {
            for (int i = 0; i < curNode.children.size(); i++) {
                curNode = curNode.children.get(i);
                add(newChild, parentElement, curNode);
            }
        }
    }

    public void printTree(Node p) {

        if (p != null) {
            for (int i = 0; i < p.children.size(); i++) {
                System.out.println("Node child of: " + p + "Name: " + p.children.get(i).element);

                Node curChild = p.children.get(i);

                for (int j = 0; j < curChild.children.size(); j++) {
                    System.out.println("Node child of : " + p.children.get(j) + "Name: " + p.children.get(j).element);
                }
            }
        }
    }
}
