package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Vertex class implements Comparable<Vertex>. The field used within the comparison is
 * their distance/cost. The constructor only requires a String id. The UGraph containing
 * the vertex must ensure the Vertex's id is unique. Each Vertex contains an array of
 * Labels used in multicast routing.
 */
public class Vertex implements Comparable<Vertex> {
    
    /** Name of this vertex */
    protected String id;

    /** weight */
    protected int dist = Integer.MAX_VALUE;
    
    /** link to the previous vertex */
    protected Vertex prev = null;
    
    protected final Map<Vertex, Integer> neighbors = new HashMap<>();

    /** Constructor requires vertex id */
    public Vertex(String id) {
        this.id = id;
    }

    protected void printPath() {
        if (this == this.prev) {
            System.out.print("    "+this.id);
        } else if (this.prev == null) {
            System.out.print(this.id + "(unreachable)");
        } else {
            this.prev.printPath();
            System.out.print("--"+this.dist+"--"+this.id);
        }
    }

    protected ArrayList<String> path = new ArrayList<>();

    // Return an array representing the path from src to dest
    // @TODO: BUG. Makes 3-node labels regardless of path size. In route nodes are not listed.
    protected ArrayList<String> pathList() {
        path.add(this.prev.prev.id);
        if (this == this.prev) {
            path.add(this.id);
        } else if (this.prev == null) {
            System.out.print(this.id + "(unreachable)");
        } else {
            path.add(this.prev.id);
            path.add(this.id);
        }
        return path;
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(dist, other.dist);
    }
}
