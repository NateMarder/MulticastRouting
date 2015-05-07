package src;

public class Main {
    private static final String SOURCE_NODE = "D";
    private static final String DEST_NODE1 = "K";
    private static final String DEST_NODE2 = "N";
    private static final String DEST_NODE3 = "L";
    private static final String DEST_NODE4 = "M";
    private static final String DEST_NODE5 = "I";
    private static final String DEST_NODE6 = "E";
    private static final String DEST_NODE7 = "H";

    public static void main(String[] args) {
        Network network = new Network();
        Network.Edge[] netsrc = network.getNet();
        UGraph graph = new UGraph(network);

        graph.findShortestPaths(DEST_NODE4);
        //graph.printPath(DEST_NODE1);
        graph.printTable(DEST_NODE2);

        graph.findShortestPaths(DEST_NODE1);
        graph.printPath(DEST_NODE4);
    }

}