package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Vertex class implements Comparable<Vertex>. The dist field is used for Comparison
 * The constructor only requires a String id. The UGraph containing the vertex
 * must ensure the Vertex's id is unique.
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

    // Return an array representing the path from src to dest
    public void pathList(ArrayList<String> pl) {
        if (this == this.prev) {
            pl.add(this.id);
        } else if (this.prev != null) {
            this.prev.pathList(pl);
            pl.add(this.id);
        }
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(dist, other.dist);
    }
}
