package src;

import java.util.*;

/**
 * Creates an undirected graph to represent a network. Nodes in the network are
 * represented as key value pairs. The key is the node name and the value a
 * Vertex. The graph is constructed from a netowork which is an array of Edges.
 *
 * @see src.Network#network
 */
public class UGraph {

    protected final Map<String, Vertex> graph;

    /**
     * Given an array of edges adds every edge to the graph
     *
     * @param network : an array of weighted edges ("src", "dest", weight)
     */
    public UGraph(Network network) {
        Network.Edge[] edges = network.getNet();
        this.graph = new HashMap<>(edges.length);

        for (Network.Edge e : edges) {

            // Adds one vertex of an edge to the graph
            String nodeOne = e.getV1();
            if (!graph.containsKey(nodeOne)) {
                graph.put(nodeOne, new Vertex(nodeOne));
            }
            // Adds the second vertex of an edge to the graph
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

    /**
     * Gets a list of node names.
     */
    public ArrayList<String> getNodesNames() {
        ArrayList<String> vertices = new ArrayList<>();
        for (Vertex v : graph.values()) {
            vertices.add(v.id);
        }
        return vertices;
    }

    /**
     * Runs dijkstra using a specified source vertex
     *
     * @param startName : starting vertex
     */
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

    /**
     * Implementation of dijkstra's algorithm using a binary heap.
     *
     * @param q : a set of vertices sorted by distance lowest to highest
     */
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


    /**
     * Returns the 2nd element of the input label
     *
     * @param label an input label used to compute the output port
     */
    protected String getOutPort(ArrayList<String> label) {
        String outputPort = new String();
        ArrayList<String> tmp = label;
        if (label.size() > 1) {
            outputPort = label.get(1).toString();
        }
        return outputPort;
    }

    /**
     * Returns the input label minus the current vertex
     *
     * @param label an input label used to compute the output label
     */
    protected List<String> outputLabel(ArrayList<String> label) {
        List<String> tmp = label.subList(1, label.size());
        return tmp;
    }

    /**
     * A table entry contains the following:
     * Input Port | Input Label | Output Port | Output Label
     *
     * @param src
     * @param dest
     */
    public String makeTable(String src, String dest) {
        String tableContent = "";

        ArrayList<String> inl = makeInputLabel(src, dest);
        String op = getOutPort(inl);
        List ol = outputLabel(inl);

        String formatNice = getSpaces(this.graph.size() - (inl.size() + 3));

        String niceINL = "";
        String niceOL = "";


        for (String next : inl) {
            niceINL += next;
        }
        for (Object next : ol) {
            niceOL += next.toString();
        }

        tableContent += "--------------------------------------------------\n" +
                "  " + src + "->" + dest + "   " + niceINL + formatNice + op.toString() +
                "             " + niceOL + "   \n";

        return tableContent;
    }

    public String makeTableEntry3(String src, String dest) {
        String tableContent = "";

        ArrayList<String> inl = makeInputLabel(src, dest);
        String op = getOutPort(inl);
        List ol = outputLabel(inl);

        String formatNice = getSpaces(this.graph.size() - (inl.size() + 3));

        String niceINL = "";
        String niceOL = "";


        for (String next : inl) {
            niceINL += next;
        }
        for (Object next : ol) {
            niceOL += next.toString();
        }

        tableContent += "--------------------------------------------------\n" +
                "  " + src + "->" + dest + "   " + niceINL + formatNice + op.toString() +
                "             " + niceOL + "   \n";

        return niceOL;
    }

    public String getSpaces(int spaces) {
        String spacey = "";
        for (int i = 0; i < spaces; i++) {
            spacey += " ";
        }
        return spacey + "      ";
    }

    /**
     * Input Label is just the path from startName to endName vertices
     */
    protected ArrayList<String> makeInputLabel(String startName, String endName) {
        ArrayList<String> tmp = new ArrayList<>();
        graph.get(endName).pathList(tmp);

        // Makes sure that the label is not empty
        if (tmp.isEmpty()) {
            tmp.add("BLANK");
        }
        return tmp;

    }


    public void startMultiCast(String src, String[] dest) {

        ArrayList<String> theLabel = new ArrayList<>();

        System.out.println("Start Node = " + src);
        for (int i = 0; i < dest.length; i++) {
            System.out.println("  -->adding label: " + src + this.makeTableEntry3(src, dest[i]));
            theLabel.add(src + this.makeTableEntry3(src, dest[i])); // to file
        }

        this.graph.get(src).labelPass(theLabel, this, true);
    }

}