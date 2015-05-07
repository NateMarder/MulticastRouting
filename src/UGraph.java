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
    private Set<Map.Entry<String, Vertex>> iterNodes;
    /**
     * Given a network of edges adds every edge to the graph Map
     * @param network : an array of weighted edges ("src", "dest", weight)
     */
    public UGraph(Network network) {
        Network.Edge[] edges = network.getNet();
        this.graph = new HashMap<>(edges.length);

        // Finds the vertices
        for (Network.Edge e : edges) {

            String nodeOne = e.getV1();
            if (!graph.containsKey(nodeOne)) {
                graph.put(nodeOne, new Vertex(nodeOne));
            }

            String nodeTwo = e.getV2();
            if (!graph.containsKey(nodeTwo)) {
                graph.put(nodeTwo, new Vertex(nodeTwo));
            }
        }

        // Sets neighboring vertices
        for (Network.Edge e : edges) {
            graph.get(e.getV1()).neighbors.put(graph.get(e.getV2()), e.getDist());

            // Makes the graph undirected
            graph.get(e.getV2()).neighbors.put(graph.get(e.getV1()), e.getDist());
        }
    }

    /** Gets a list of node names. */
    public ArrayList<String> getNodesNames() {
        ArrayList<String> vertices = new ArrayList<>();
        for (Vertex v : graph.values()) {
            vertices.add(v.id);
        }
        return vertices;
    }

    /** Exposes the vertices. */
    public ArrayList<Vertex> getVertices() {
        ArrayList<Vertex> vertex = new ArrayList<>();
        for (Vertex v : graph.values()) {
            vertex.add(v);
        }
        return vertex;
    }

    /** Runs dijkstra using a specified source vertex
     * @param startName : starting vertex
     * */
    public void findShortestPaths(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }

        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            if (v == source) {
                v.prev = source;
            } else {
                v.prev = null;
            }
            if (v == source) {
                v.dist = 0;
            } else {
                v.dist = Integer.MAX_VALUE;
            }
            q.add(v);
        }

        findShortestPaths(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap.
     * @param q : a set of vertices sorted by distance lowest to highest
     * */
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

    /** Prints a path from the source to the specified vertex
     * @param endName : destination vertex
     * */
    public void printPath(String endName) {

        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }
        graph.get(endName).printPath();
    }

    protected void printTable(String endName) {
        graph.get(endName).pathList();
    }

    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}