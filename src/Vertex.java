package src;

import java.util.HashMap;
import java.util.Map;


/**
 * Vertex class implements Comparable<Vertex>. The field used within the comparison is
 * their distance/cost. The constructor only requires a String id. The UGraph containing
 * the vertex must ensure the Vertex's id is unique. Each Vertex contains an array of
 * Labels used in multicast routing.
 */
public class Vertex implements Comparable<Vertex> {
    protected String id;
    protected int dist = Integer.MAX_VALUE;
    protected Vertex prev = null;
    protected final Map<Vertex, Integer> neighbors = new HashMap<>();

    /** Constructor requires vertex id & network size */
    public Vertex(String id) {
        this.id = id;
    }

    protected void printPath() {
        if (this == this.prev) {
            System.out.print("    "+this.id); // Source Node
        } else if (this.prev == null) {
            System.out.print(this.id + "(unreachable)");
        } else {
            this.prev.printPath();
            System.out.print( "<-"+this.dist+"->"+this.id);
        }
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(dist, other.dist);
    }
}
