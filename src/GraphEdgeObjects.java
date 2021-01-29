import java.util.*;

//Object oriented graph representation. //Towers = vertices, cables = edges
public class GraphEdgeObjects {
    Tower[] towers;
    ArrayList<Edges> cables;

    public GraphEdgeObjects () {
        cables = new ArrayList<>();
    }

    public static void main(String[] args) {
        int i = 0;
        Tower[] towers = new Tower[7];

        Tower T1 = new Tower("T1");
        towers[i++] = T1;
        Tower T2 = new Tower("T2");
        towers[i++] = T2;
        Tower T3 = new Tower("T3");
        towers[i++] = T3;
        Tower T4 = new Tower("T4");
        towers[i++] = T4;
        Tower T5 = new Tower("T5");
        towers[i++] = T5;
        Tower T6 = new Tower("T6");
        towers[i++] = T6;
        Tower T7 = new Tower("T7");
        towers[i++] = T7;

        GraphEdgeObjects g = new GraphEdgeObjects();
        g.addTowers(towers);
        g.addCable(T1, T2, 10);
        g.addCable(T1, T4, 5);
        g.addCable(T4, T2, 6);
        g.addCable(T2, T5, 3);
        g.addCable(T4, T6, 11);
        g.addCable(T5, T6, 4);
        g.addCable(T7, T5, 17);
        g.addCable(T7, T6, 10);
        g.addCable(T3, T5, 7);

        //Most expensive way to connect all towers:
        ArrayList<Edges> longest = g.kruskalsEx();
        int sumMax = 0;
        for (Edges e: longest) {
            sumMax += e.weight;
        }
        System.out.println("Maximum cable cost: " + sumMax + " km");
        System.out.println();

        //Cheapest way to connect all towers:
        ArrayList<Edges> cheapest = g.kruskals();
        int sumMin = 0;
        for (Edges e: cheapest) {
            sumMin += e.weight;
        }

        System.out.println("Cheapest way to connect all towers: " + sumMin + " km");
        System.out.println("Calculated using Kruskals algorithm. Other algorithms solving same problem: Prim, Borůvka");
    }

    public void addCable(Tower from, Tower to, int weight) {
        Edges cabel = new Edges(from, to, weight);
        cables.add(cabel);
    }
    private void addTowers(Tower[] t) {
        towers = t;
    }

    //Finds the most expensive network with Kruskals algorithm in O(VlogE) time
    private ArrayList<Edges> kruskalsEx() {
        Deque<Edges> Q = new ArrayDeque<>();
        HashMap<Integer,Tower> C = new HashMap<>();
        ArrayList<Edges> T = new ArrayList<>();
        Collections.sort(cables);

        //Sort edges by weight in deque
        for (Edges e: cables) {
            Q.addFirst(e);
        }
        //Define clusters
        for (int i = 0; i < towers.length; i++) {
            Set<Tower> set = new HashSet<>();
            set.add(towers[i]);
            towers[i].cluster = set;
        }
        //While tree has less than number of towers - 1 edges
        //-->Pick the current longest cable
        while (T.size() < towers.length-1) {
            Edges e = Q.poll();

            //If not cycle (same cluster)
            if (e.to.cluster != e.from.cluster) {
                T.add(e);
                //Merge the clusters
                e.to.cluster.addAll(e.from.cluster);
                e.from.cluster = e.to.cluster;
            }
        }
        return T;
    }

    //Finds shortest cable network with Kruskals algorithm in O(VlogE) time
    private ArrayList<Edges> kruskals() {
        PriorityQueue<Edges> Q = new PriorityQueue<>();
        ArrayList<Edges> T = new ArrayList<>();

        //Sort edges by weight in queue
        for (Edges e: cables) {
            Q.add(e);
        }
        //Define clusters
        for (int i = 0; i < towers.length; i++) {
            Set<Tower> set = new HashSet<>();
            set.add(towers[i]);
            towers[i].cluster = set;
        }
        //While tree has less than number of towers - 1 edges
        //-->Pick the current shortest cable
        while (T.size() < towers.length-1) {
            Edges e = Q.remove();

            //If not cycle (same cluster)
            if (e.to.cluster != e.from.cluster) {
                T.add(e);
                //Merge the clusters
                e.to.cluster.addAll(e.from.cluster);
                e.from.cluster = e.to.cluster;
            }
        }
        return T;
    }
}
