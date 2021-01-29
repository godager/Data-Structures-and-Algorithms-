import java.util.HashSet;
import java.util.Set;

public class Tower implements Comparable<Tower>{
    String name;
    int D;
    Set<Tower> cluster;

    public Tower(String n) {
        name = n;
        cluster = new HashSet<>();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Tower o) {
        return this.D - o.D;
    }
}