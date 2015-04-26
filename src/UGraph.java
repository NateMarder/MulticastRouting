package src;

import java.util.*;
/**
 * Creates an undirected graph to represent a network.
 * Nodes in the network are represented as key value pairs. The key is the node
 * name and the value a Vertex. The graph is created by adding edges. Each
 * edge has a source, destination and a distance (or cost). This way when
 * looking for paths we can compare edge costs and only keep the lowest cost
 * edge in the path list.
 *
 */
public class UGraph {

    private HashMap<String, Vertex> graph;

    /** Constructor Requires a Network */
    public UGraph(Network network) {
        Edge[] edges = network.getNet();
        graph = new HashMap<>(edges.length);

        // Finds the vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        // Sets neighboring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);

            // Makes the graph undirected
            graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist);
        }
    }

    // Used in the constructor
    public static class Edge {
        private String v1;
        private String v2;
        private int dist;

        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }

        protected void setDist(int dist) {
            this.dist = dist;
        }
    }

    //Implements Comparable so that we can compare vertices by their distance (i.e. cost)
    public static class Vertex implements Comparable<Vertex> {
        private final String id;
        private int dist = Integer.MAX_VALUE;
        private Vertex prev = null;
        private final Map<Vertex, Integer> neighbours = new HashMap<>();

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

    /** Runs dijkstra using a specified source vertex and a heap implementation */
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
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
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

    /** Prints a path from the source to the specified vertex */
    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("UGraph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }
        graph.get(endName).printPath();
        System.out.println();
    }

    /** Prints the path from the source to every vertex */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}
