package src;

public class Main {

    /** Tests multicast simulation by creating a network of vertices and displaying
     * an MPLS routing table from each node to every other node.
     *
     * @see src.Network
     * @see src.UGraph
     * @see src.Vertex
     * @param args
     */
    public static void main(String[] args) {
        Network network = new Network();
        UGraph graph = new UGraph(network);

        for (String src : graph.getNodesNames()) {
            // Run shortest path first to set up the distances and paths
            graph.findShortestPaths(src);

            // Table header
            System.out.printf("---BEGIN TABLE FOR NODE %s---\n", src);

            // Sets a source and makes an MPLS table entry to every other vertex
            for (String dest : graph.getNodesNames()) {
                graph.makeTableEntry(src, dest);
            }

            // Spaces between tables looks nice
            System.out.printf("\n\n\n");
        }

    }
}