package src;

import java.util.Random;

/**
 * Represents the network illustrated in the groups project handout.
 * The syntax to create a new edge is ("vertex-1", "vertex-2", int cost).
 * This network can be passed to Main.setRandomCosts() and the hardcoded costs
 * will be overwritten with random costs. This is how we fulfill the project
 * requirements of generating random costs for every edge.
 */
public class Network {

    protected String nodes[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"}; // size = 14

    private Edge[] network = {
            new Edge("A", "B"),
            new Edge("B", "D"),
            new Edge("B", "K"),
            new Edge("C", "F"),
            new Edge("D", "E"),
            new Edge("E", "F"),
            new Edge("E", "G"),
            new Edge("F", "G"),
            new Edge("F", "N"),
            new Edge("G", "H"),
            new Edge("H", "I"),
            new Edge("I", "G"),
            new Edge("I", "L"),
            new Edge("I", "M"),
            new Edge("A", "C"),
            new Edge("K", "L"),
            new Edge("K", "M"),
            new Edge("L", "N"),
            new Edge("M", "N"),
    };

    protected Edge[] getNet() {
        return network;
    }
    /**
     * The Edge class contains string representation of nodes connected by the Edge.
     * The field dist represents the cost which is used to compute the shortest
     * path algorithm.
     */
    protected class Edge {
        private String v1;
        private String v2;
        private static final int MAX_COST = 20;
        private Random rand = new Random();
        private int dist;

        public Edge(String v1, String v2) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = rand.nextInt((MAX_COST) - 1) + 1;
        }

        public String getV1() {
            return v1;
        }

        public String getV2() {
            return v2;
        }

        public int getDist() {
            return dist;
        }
    }
}