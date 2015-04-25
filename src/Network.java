/**
 * Represents the network illustrated in the groups project handout.
 * The syntax to create a new edge is ("vertex-1", "vertex-2", int cost).
 *
 * @TODO: randomly generate the cost for each edge.
 */
public class Network {
    private static final UGraph.Edge[] network = {
        new UGraph.Edge("1", "2", 6),
        new UGraph.Edge("1", "3", 7),
        new UGraph.Edge("1", "8", 12),
        new UGraph.Edge("2", "4", 2),
        new UGraph.Edge("2", "11", 18),
        new UGraph.Edge("3", "6", 8),
        new UGraph.Edge("4", "5", 1),
        new UGraph.Edge("5", "6", 4),
        new UGraph.Edge("5", "7", 2),
        new UGraph.Edge("6", "10", 7),
        new UGraph.Edge("6", "14", 18),
        new UGraph.Edge("7", "8", 1),
        new UGraph.Edge("8", "9", 3),
        new UGraph.Edge("9", "10", 7),
        new UGraph.Edge("9", "12", 3),
        new UGraph.Edge("9", "13", 3),
        new UGraph.Edge("1", "3", 7),
        new UGraph.Edge("11", "12", 4),
        new UGraph.Edge("11", "13", 5),
        new UGraph.Edge("12", "14", 5),
        new UGraph.Edge("13", "14", 1),
    };
}
