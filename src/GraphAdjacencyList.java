import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.min;

/*Graph represented by the vertices array where each vertex object
// has a adjacency list to vertices they are connected to */
public class GraphAdjacencyList {
    private String filename;
    private Scanner in;
    private VertexObject[] vertices;
    private ArrayList<VertexObject> sep_vertices = new ArrayList<>();
    private boolean cycle = false;

    //Constructor for traversing: and sep- vertices
    public GraphAdjacencyList (String file) throws FileNotFoundException {
        filename = file;
        in = new Scanner(new File(filename));
        readFile();
    }

    //Constructor for testing shortest path:
    public GraphAdjacencyList () {
        vertices = new VertexObject[4];
        int i = 0;
        VertexObject A = new VertexObject(1);
        VertexObject B = new VertexObject(2);
        VertexObject C = new VertexObject(3);
        VertexObject D = new VertexObject(4);

        vertices[i++] = A;
        vertices[i++] = B;
        vertices[i++] = C;
        vertices[i++] = D;

        A.edges = new HashSet<>();
        A.edges.add(new Edge(B, 3));
        A.outEdges.add(B);

        B.edges = new HashSet<>();
        B.edges.add(new Edge(C, 6));
        B.outEdges.add(C);

        C.edges = new HashSet<>();
        C.edges.add(new Edge(D, 1));
        C.outEdges.add(D);

        D.edges = new HashSet<>();
        D.edges.add(new Edge(A, 1));
        D.outEdges.add(A);
    }

    /*File format:
    4                       --> number of vertices
    1        4        0     --> vertex 1 has edge to 4 and no other edge (terminated by a 0)
     */

    private void readFile() {
        int n = in.nextInt();
        vertices = new VertexObject[n];

        for (int i = 0; i < n; i++) {
            vertices[i] = new VertexObject(i + 1);
        }

        //Add VertexObject
        for (int i = 0; i < n; i++) {
            int id = in.nextInt();
            VertexObject VertexObject = vertices[id - 1];

            //Add edges from VertexObject
            while (true) {
                int dep = in.nextInt();
                if (dep == 0) {
                    break;
                }
                vertices[dep - 1].addEdge(VertexObject);
                vertices[id - 1].cntPredecessors++;
            }
        }
    }

    //Dijktra's algorithm. Returns shortest path from s to all other vertices O(E logV)
    private int[] dijkstra (VertexObject[] G, VertexObject s) {
        PriorityQueue<VertexObject> Q = new PriorityQueue<>();
        int [] D = new int [G.length];
        for (int i = 0; i <G.length ; i++) {
            G[i].D = Integer.MAX_VALUE;
            Q.add(G[i]);
        }
        s.D = 0;

        while (!Q.isEmpty()) {
            VertexObject v = Q.remove();
            for (Edge e: v.edges) {
                if ((v.D + e.weight) < (e.dest.D)) {
                   e.dest.D = v.D + e.weight;
                    Q.add(e.dest);
                }
            }
        }
        return D;
    }

    //Bellman-Ford. For negative edge weight. Returns shortest path from s to all other vertices in O(V*E) time
    private int[] bellmanFord (VertexObject[] G, VertexObject s) {
        int [] D = new int [G.length];
        //Initialize all distances to infinity
        for (int i = 0; i <G.length ; i++) {
            G[i].D = Integer.MAX_VALUE;
        }
        //distance from start to start is 0 (not so weird, right?)
        s.D = 0;

        //Loops trough every vertex and all its edges, updating shortest distance
        //(Shortest path can maximum have |V| - 1 edges)
        for (int i = 0; i < G.length - 1; i++) {
            VertexObject u = G[i];
            for (Edge e: u.edges) {
                //If shorter path is found, update distance in vertex
                if (u.D + e.weight < e.dest.D) {
                    e.dest.D = u.D + e.weight;
                }
            }
        }
        //Check for negative-weight cycles. If we can find a shorter path after last step
        //the graph must have negative cycle
        for (VertexObject u: vertices) {
            for (Edge e: u.edges) {
                if (u.D + e.weight < e.dest.D && u.D != Integer.MAX_VALUE) {
                    System.out.println("G has a negative cycle");
                    return null;
                }
            }
        }
        return D;
    }

    //Hopcroft-Tarjan. Returns separation vertices.
    private ArrayList<VertexObject> hopcroftTarjan (VertexObject[] G, VertexObject u, int depth) {
        u.visited = true;
        u.low = u.index = depth;
        u.childCount = 0;

        //Follow edges. For each descendant:
        for (VertexObject v: u.outEdges) {
            if (!v.visited) {
                u.childCount += 1;
                hopcroftTarjan(G, v,depth + 1);

                //Update low now that all vertices is visited with dfs
                u.low = min(u.low, v.low);

                //If not root (not the first), and index <= low number to any descendant
                if (u.index != 1) {
                    if (u.index <= v.low) {
                        //u is separation Vertex
                        sep_vertices.add(u);
                    }
                }
            }
            else {
                //One back edge found. Update low one last time
                u.low = min(u.low, v.index);
            }
        }
        //First VertexObject sep? (The root)
        if (u.index == 1) {
            if (u.childCount > 1) {
                sep_vertices.add(u);
            }
        }
        return sep_vertices;
    }

    //Topological sorting in O(E+V)
    private VertexObject[] topologicalSort(VertexObject[] graph){
        ArrayDeque<VertexObject> deque = new ArrayDeque<>();

        //Start with vertices with no predecessors
        for(VertexObject t: graph) {
            if(t.cntPredecessors == 0){
                deque.add(t);
            }
        }
        int i = 1;
        VertexObject[] output = new VertexObject[graph.length];
        while(!deque.isEmpty()){
            VertexObject temp = deque.poll();
            output[i] = temp;
            i++;

            //For each edge - remove dependencies
            for (VertexObject t2:temp.outEdges) {
                t2.cntPredecessors--;
                if(t2.cntPredecessors == 0) deque.add(t2);
            }
        }
        //If all tasks have been successfully topologically sorted there is no cycle
        if (i <= graph.length) {
            cycle = true;
            return null;
        }
        return output;
    }

    //DFS Depth first search visiting all nodes that could be reached from s:
    //O(V+E)
    private void dfs (VertexObject[] G, VertexObject s) {
        s.visited = true;

        for (VertexObject v: s.outEdges) {
            if (!v.visited) dfs(G, v);
        }
    }
    //DFS from all nodes :
    private void dfsFull(VertexObject[] G) {
        for (int i = 0; i < G.length; i++) {
            if (!G[i].visited) dfs(G, G[i]);
        }
    }

    //BFS Visits the vertices in the graph layer by layer
    private void bfs (VertexObject[]G, VertexObject s) {
        s.visited = true;
        LinkedList<VertexObject> queue = new LinkedList<VertexObject>();
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();
        }

        //Visit all adjacent vertices before moving on to next layer
        for (VertexObject v: s.outEdges) {
            if (!v.visited) {
                v.visited = true;
                queue.add(v);
            }
        }
    }

    //test run
    public static void main (String[] args) throws IOException {
        //String filename = args[0];
        GraphAdjacencyList g = new GraphAdjacencyList();
        ArrayList<VertexObject> sep = g.hopcroftTarjan(g.vertices, g.vertices[0], 1);
        for (VertexObject v: sep) {
            System.out.println(v);
        }

        int [] D = g.bellmanFord(g.vertices, g.vertices[0]);
        for (int i = 0; i < (D != null ? D.length : 0) - 1; i++) {
            System.out.println(D[i]);
        }
    }
}


