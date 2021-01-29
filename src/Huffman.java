import java.util.HashMap;
import java.util.PriorityQueue;

//Tree structure used for lossless data compression
public class Huffman {

    private Node root;

    public Huffman () {
        root = new Node(null, 0, null, null);
    }

    class Node implements Comparable<Node>{
        protected Node left = null;
        protected Node right = null;
        protected Character symbol;
        protected int frequency;

        public Node(Character s, int f, Node left, Node right) {
            symbol = s;
            frequency = f;
        }

        @Override
        public int compareTo(Node o) {
            return (frequency - o.frequency);
        }
    }

    private void buildHuffmanTree (HashMap<Character, Integer> C) {
        PriorityQueue<Node> Q = new PriorityQueue<>();

        //Add nodes on priority queue with their frequencies
        for (Character key: C.keySet()) {
            int frequency = C.get(key);
            Q.offer(new Node(key, frequency, null, null));
        }

        //Create parent node with frequency = sum of the two child nodes
        while (Q.size() > 1) {
            Node v1 = Q.remove();
            Node v2 = Q.remove();
            int f = v1.frequency + v2.frequency;
            Q.offer(new Node(null, f, v1, v2));
        }
        root = Q.remove();
    }

    private void printHuffmanTree(Node v) {
        System.out.println(v);
        printHuffmanTree(v.left);
        printHuffmanTree(v.right);
    }
}
