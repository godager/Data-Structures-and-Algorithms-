import java.util.ArrayList;
import java.util.Set;

public class VertexObject implements Comparable<VertexObject>{
    String name;
    int id;
    ArrayList<VertexObject> outEdges = new ArrayList<>();
    ArrayList<VertexObject> inEdges = new ArrayList<>();
    Set<Edge> edges;
    public int cntPredecessors;
    boolean visited = false;
    boolean sep = false;
    int low;
    int index;
    int childCount;
    int D;

    public VertexObject (int i) {
        id = i;
    }

    public void addEdge(VertexObject v) {
        outEdges.add(v);
    }

    public void addInEdge(VertexObject v) {
        inEdges.add(v);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int compareTo(VertexObject o) {
        return D - o.D;
    }
}