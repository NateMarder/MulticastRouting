package src;

import java.util.*;

/**
 * Creates an undirected graph to represent a network.
 * Nodes in the network are represented as key value pairs. The key is the node
 * name and the value a Vertex. The graph is created by adding edges. Each
 * edge has a source, destination and a distance (or cost). This way when
 * looking for paths we can compare edge costs and only keep the lowest cost
 * edge in the path list.
 */
public class UGraph {

    private HashMap<String, Vertex> graph;

    /**
     * Constructor Requires a Network
     */
    public UGraph(Network network) {
        Network.Edge[] edges = network.getNet();
        this.graph = new HashMap<>(edges.length);

        // Finds the vertices
        for (Network.Edge e : edges) {
            if (!graph.containsKey(e.getV1())) graph.put(e.getV1(), new Vertex(e.getV1()));
            if (!graph.containsKey(e.getV2())) graph.put(e.getV2(), new Vertex(e.getV2()));
        }

        // Sets neighboring vertices
        for (Network.Edge e : edges) {
            graph.get(e.getV1()).neighbors.put(graph.get(e.getV2()), e.getDist());

            // Makes the graph undirected
           graph.get(e.getV2()).neighbors.put(graph.get(e.getV1()), e.getDist());
        }
    }

    /**
     * Runs dijkstra using a specified source vertex and a heap implementation
     */
    public void dijkstra(String startName) {
        Vertex u, v;

        if (!graph.containsKey(startName)) {
            System.err.printf("UGraph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }

        final Vertex source = graph.get(startName);

        NavigableSet<Vertex> treeSet = new TreeSet<>();

        for (Vertex vertex : graph.values()) {
            if (vertex == source) {
                vertex.prev = source;
                vertex.dist = 0;
            } else {
                vertex.prev = null;
                vertex.dist = Integer.MAX_VALUE;
            }
            treeSet.add(vertex);
        }

        while (!treeSet.isEmpty()) {

            u = treeSet.pollFirst(); // vertex with shortest distance (first iteration will return source)
            if (u.dist == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbors.entrySet()) {
                v = a.getKey(); //the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    treeSet.remove(v);
                    v.dist = alternateDist;
                    v.prev = u;
                    treeSet.add(v);
                }
            }
        }
    }

    public void makeLabels() {

        for (Vertex v : graph.values()) {
            v.setUpLabels(this.getGraph());
        }
    }

    /**
     * Prints the path from the source to every vertex
     */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }

    /**
     * Prints a path from the source to the specified vertex
     */
    public void printPath(String destVertex) {
        if (!graph.containsKey(destVertex)) {
            System.err.printf("UGraph doesn't contain end vertex \"%s\"\n", destVertex);
            return;
        }
        System.out.println(" With Costs---->");
        graph.get(destVertex).printPath(); // Vertex toString()
        System.out.println("\n With Labels--->");
        graph.get(destVertex).printPathWithLabels(destVertex);
        System.out.println("\n\n\n All Labels:");
        for (Vertex vert: graph.values()){
            vert.printLabels(graph.get(destVertex));
        }
    }

    public HashMap<String, Vertex> getGraph() {
        return graph;
    }


    /**
     * Vertex class implements Comparable<Vertex>. The field used within the comparison is
     * their distance/cost. The constructor only requires a String id. The UGraph containing
     * the vertex must ensure the Vertex's id is unique. Each Vertex contains an array of
     * Labels used in multicast routing.
     */
    public static class Vertex implements Comparable<Vertex> {
        private final String id;
        private int dist = Integer.MAX_VALUE;
        private Vertex prev = null;
        private final Map<Vertex, Integer> neighbors = new HashMap<>();
        private HashMap<String, Integer> labels = new HashMap<>();


        private void printPath() {
            if (this == this.prev) {
                System.out.print("    "+this.id); // Source Node
            } else if (this.prev == null) {
                System.out.print(this.id + "(unreachable)");
            } else {
                this.prev.printPath();
                if (this.dist<10){
                    System.out.print( " <- "+this.dist+"-> "+this.id);
                }else{
                    System.out.print( " <-"+this.dist+"-> "+this.id);
                }
            }
        }

        private void printPathWithLabels(String destVertex) {
            if (this == this.prev) {
                System.out.print("    "+this.id); // Source Node
            } else if (this.prev == null) {
                System.out.print(this.id + "(unreachable)");
            } else {
                int sendLabel = this.prev.labels.get(destVertex);
                this.prev.printPathWithLabels(destVertex);
                System.out.print(" -" + sendLabel + "-> " + this.id);
            }
        }

        private void printLabels(Vertex dest) {
            if (!this.labels.isEmpty()) {
                System.out.print("\n"+this.id+" Labels for Destinations: ");
                for (String s : labels.keySet()) {
                    if (s.matches(dest.id)){
                        System.out.printf("\n  %s: %s (Sending THIS)", s, labels.get(s));
                    } else{
                        System.out.printf("\n  %s: %s", s, labels.get(s));
                    }
                }
            }
        }

        public Vertex(String id) {
            this.id = id;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(dist, other.dist);
        }

        private void setUpLabels(HashMap<String, Vertex> graph) {
            Random ran = new Random();
            String labelKey;
            int labelValue;

            for (Vertex vertex : graph.values()) {
                int nextLabelID = ran.nextInt(Integer.MAX_VALUE);
                while (labels.containsValue(Integer.MAX_VALUE)) {
                    nextLabelID = ran.nextInt(Integer.MAX_VALUE);
                }
                labelKey = vertex.id;
                labelValue = nextLabelID;
                this.labels.put(labelKey, labelValue);
            }
        }
    }
}
