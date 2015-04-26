package src;

import java.util.Random;

/**
 * Represents the network illustrated in the groups project handout.
 * The syntax to create a new edge is ("vertex-1", "vertex-2", int cost).
 *
 */

public class Network {

    protected static UGraph.Edge[] network = {
        new UGraph.Edge("A", "B", 6),
        new UGraph.Edge("B", "D", 2),
        new UGraph.Edge("B", "K", 18),
        new UGraph.Edge("C", "F", 8),
        new UGraph.Edge("D", "E", 1),
        new UGraph.Edge("E", "F", 4),
        new UGraph.Edge("E", "G", 2),
        new UGraph.Edge("F", "G", 7),
        new UGraph.Edge("F", "N", 18),
        new UGraph.Edge("G", "H", 1),
        new UGraph.Edge("H", "I", 3),
        new UGraph.Edge("I", "G", 7),
        new UGraph.Edge("I", "L", 3),
        new UGraph.Edge("I", "M", 3),
        new UGraph.Edge("A", "C", 7),
        new UGraph.Edge("K", "L", 4),
        new UGraph.Edge("K", "M", 5),
        new UGraph.Edge("L", "N", 5),
        new UGraph.Edge("M", "N", 1),
    };

    protected UGraph.Edge[] getNet(){
        return network;
    }

}
