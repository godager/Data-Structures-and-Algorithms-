import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
//Graph represented with an array of arrays
public class Graph2DMatrix {
    private String filename;
    private Scanner in;
    private VertexObject[] vertices;
    private int[][] matrix;
    private Stack<VertexObject> stack = new Stack<>();

    public Graph2DMatrix (String file) throws FileNotFoundException {
        filename = file;
        in = new Scanner(new File(filename));
        readFile();
    }

    //File format:
    //8   --> number of vertices
    //0 1 --> edge from vertex with value 0 to vertex with value 1
    //1 0 --> edge from 1 to o
    private void readFile() {
        int n = in.nextInt();

        //Matrix
        matrix = new int[n][n];

        //Graph vertices
        vertices = new VertexObject[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new VertexObject(i);
        }

        //Edges
        while (in.hasNextInt()) {
            int from = in.nextInt();
            int to = in.nextInt();
            setEdge(from, to);
        }
    }

    //Finds the vertices that are strongly connected. (Every vertex can reach each other)
    private void findStrongComp() {
        //DFS 1
        boolean[] b1 = visitArray();
        strongComp(vertices, matrix, vertices[0], b1);

        //DFS 2
        boolean[] b2 = visitArray();
        int counter = 1;
        while (!stack.isEmpty()) {
            //If vertex is visited we pop it
            if (b2[stack.peek().id]) {
                stack.pop();
            }
            else {
                //start dfs on reversed graph to print strong components:
                System.out.println();
                System.out.println();
                System.out.println("Strong components group " + counter++ + ":");
                strongCompReversed(stack.peek(), vertices, b2);
            }
        }
    }

    //Pushes vertices on stack in DFS
    private void strongComp (VertexObject[] G, int[][] m, VertexObject u, boolean visited[]) {
        visited[u.id] = true;

        //For each out edge
        for (int i = 0; i < G.length; i++) {
            if (m[u.id][i] == 1) {
                VertexObject v = vertices[i];
                if (!visited[v.id]) {
                    strongComp(G, m, v, visited);
                }
            }
        }
        stack.push(u);
    }

    //DFS on reversed graph
    private void strongCompReversed (VertexObject u, VertexObject[]G, boolean[] visited) {
        visited[u.id] = true;
        System.out.print(u);

        //For each out edge
        for (int i = 0; i < G.length; i++) {
            //We reverse the edge by saying [u.id][i] = [i][u.id]:
            if (matrix[i][u.id] == 1) {
                VertexObject v = vertices[i];
                if (!visited[v.id]) {
                    strongCompReversed(v, G, visited);
                }
            }
        }
    }

    //Creates visit array with false on all indexes:
    private boolean[] visitArray () {
        boolean[] b = new boolean[vertices.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = false;
        }
        return b;
    }

    private void setEdge (int from, int to) {
            matrix[from][to] = 1;
    }

    private void printMatrix() {
        System.out.println();
        for (int i = 0; i < vertices.length; i++) {
            System.out.println();
            for (int j = 0; j < vertices.length; j++) {
                System.out.print(matrix[i][j]);
            }
        }
    }

    //
    public static void main (String[] args) throws IOException {
        String filename = args[0];
        Graph2DMatrix g = new Graph2DMatrix(filename);
        g.printMatrix();
        g.findStrongComp();
    }
}