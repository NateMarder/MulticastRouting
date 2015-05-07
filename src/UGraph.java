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

    private HashMap<String, Vertex> graph;
    private String[] nodes;
    private int size;

    /**
     * Constructor Requires a Network
     */
    public UGraph(Network network) {
        Network.Edge[] edges = network.getNet();
        this.graph = new HashMap<>(edges.length);
        this.nodes = network.nodes;
        this.size = nodes.length;

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

    public HashMap<String, Vertex> getGraph() {
        return graph;
    }
}