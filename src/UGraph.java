package src;

import java.util.*;

/**
 * Creates an undirected graph to represent a network.
 * Nodes in the network are represented as key value pairs. The key is the node
 * name and the value a Vertex. The graph is created by adding edges. Each
 * edge has a source, destination and a distance (or cost). This way when
 * looking for theLabels we can compare edge costs and only keep the lowest cost
 * edge in the path list.
 */
public class UGraph {

    private final Map<String, Vertex> graph;
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

    /** Runs dijkstra using a specified source vertex */
    public void findShortestPaths(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.prev = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        findShortestPaths(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void findShortestPaths(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            // vertex with shortest distance (first iteration will return source)
            u = q.pollFirst();

            // we can ignore u (and any other remaining vertices) since they are unreachable
            if (u.dist == Integer.MAX_VALUE) break;

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbors.entrySet()) {
                v = a.getKey(); //the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.prev = u;
                    q.add(v);
                }
            }
        }
    }

    /** Prints a path from the source to the specified vertex */
    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }
    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}