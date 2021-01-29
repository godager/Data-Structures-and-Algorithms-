public class Edges implements Comparable<Edges> {
    Tower to;
    int weight;
    Tower from;

    public Edges(Tower f, Tower t, int w) {
        to = t;
        from = f;
        weight = w;
    }

    @Override
    public int compareTo(Edges o) {
        return this.weight - o.weight;
    }
    @Override
    public String toString () {
        return from + "--"+weight+"--"+to;
    }
}
