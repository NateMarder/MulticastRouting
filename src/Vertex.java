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
        System.out.printf("printPath called by node %s\n", this.id);
        if (this == this.prev) {
            System.out.print("This == this.prev...");
            System.out.println(this.id);
        } else if (this.prev == null) {
            System.out.print(this.id + "(unreachable)");
        } else {
            System.out.println("going back up...");
            this.prev.printPath();

            // HOW THE FUCK DOES IT GET HERE!!??
            System.out.print("Going down the path...");
            System.out.print(this.id);
        }
    }

    protected ArrayList<String> path = new ArrayList<>();

    // Trying to get a path from src to dest and put into an ArrayList
    protected ArrayList<String> pathList() {

        System.out.printf("printPath called by node %s\n", this.id);
        if (this == this.prev) {
            System.out.print("This == this.prev...");
            System.out.println(this.id);
            path.add(this.id);
        } else if (this.prev == null) {
            System.out.print(this.id + "(unreachable)");
        } else {
            System.out.println("going back up...");
            path.add(this.id);
            path.add(this.prev.id);
            this.prev.printPath();

            // HOW THE FUCK DOES IT GET HERE!!??
            System.out.print("Going down the path...");
            System.out.print(this.id);
            path.add(this.)
        }
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(dist, other.dist);
    }
}
