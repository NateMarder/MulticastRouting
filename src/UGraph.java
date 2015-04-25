import java.util.HashMap;
import java.util.Map;

/**
 * Creates an undirected graph to represent a network. 
 * Nodes in the network are represented as key value pairs. The key is the node 
 * name and the value a Vertex. The graph is created by adding edges. Each 
 * edge has a source, destination and a distance (or cost). This way when 
 * looking for paths we can compare edge costs and only keep the lowest cost 
 * edge in the path list.
 *
 * @TODO: Find paths from every node to every other node.
 */
public class UGraph {

    // The entire graph
    private final HashMap<String, Vertex> graph;

    // Used in the constructor
    public static class Edge {
        public final String v1, v2;
        public final int dist;
        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    // Implements Comparable so that we can compare vertices by their distance (i.e. cost)
    public static class Vertex implements Comparable<Vertex> {
        public final String id;
        public int dist = Integer.MAX_VALUE;
        public Vertex prev = null;
        public final Map<Vertex, Integer> neighbors = new HashMap<>();

        private void printPath() {
            if (this == this.prev) {
                System.out.printf("%s", this.id);
            } else if (this.prev == null) {
                System.out.printf("%s(unreachable)", this.id);
            } else {
                this.prev.printPath();
                System.out.printf(" -> %s(%d)", this.id, this.dist);
            }
        }

        public Vertex(String id) {
            this.id = id;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(dist, other.dist);
        }
    }

    /** Builds a graph from a set of edges */
    public UGraph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        // Finds the vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        // Sets neighboring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbors.put(graph.get(e.v2), e.dist);

            // Makes the graph undirected
            graph.get(e.v2).neighbors.put(graph.get(e.v1), e.dist);
        }
    }
}
