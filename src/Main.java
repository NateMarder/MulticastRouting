package src;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    private static final String SOURCE_NODE = "D";
    private static final String DEST_NODE1 = "K";
    private static final String DEST_NODE2 = "N";
    private static final String DEST_NODE3 = "L";
    private static final String DEST_NODE4 = "M";
    private static final String DEST_NODE5 = "I";
    private static final String DEST_NODE6 = "E";
    private static final String DEST_NODE7 = "H";

    public static void mplsTable() {

        Network net = new Network();
        UGraph g = new UGraph(net);
        ArrayList<String> members = new ArrayList<>();
        members.add("D");
        members.add("K");
        members.add("L");
        members.add("I");
        members.add("E");
        members.add("H");

        for (String node : members) {
            g.findShortestPaths(node);
            for (String neighbor : members) {
                g.printPath(neighbor);
            }
        }
    }

    public static void main(String[] args) {

        mplsTable();
    }

}