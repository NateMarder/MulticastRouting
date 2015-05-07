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

    private Edge[] network;

    public Network() {
        network = new Edge[]{
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
                new Edge("J", "I"),
                new Edge("J", "F"),
                new Edge("A", "C"),
                new Edge("K", "L"),
                new Edge("K", "M"),
                new Edge("L", "N"),
                new Edge("M", "N"),
        };
    }

    protected Edge[] getNet() {
        return network;
    }
    /**
     * The Edge class contains string representation of nodes connected by the Edge.
     * The field dist represents the cost which is used to compute the shortest
     * path algorithm.
     */
    protected class Edge {
        /** first vertex */
        private String v1;

        /** second vertex */
        private String v2;

        /** Arbitrary maximum cost */
        private static final int MAX_COST = 20;

        /** Used to generate a random weight */
        private Random rand = new Random();

        /** Distance, cost, weight, whatever */
        private int dist;

        /**
         * Makes an weighted edge from two vertexes and a random weight
         * @param v1 : name of first vertex
         * @param v2 : name of second vertex
         */
        public Edge(final String v1, final String v2) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = rand.nextInt((MAX_COST) - 1) + 1;
        }

        /** @return one of the vertices */
        public String getV1() {
            return this.v1;
        }

        /** @return the other vertex */
        public String getV2() {
            return this.v2;
        }

        /** @return the weight of this edge */
        public int getDist() {
            return this.dist;
        }
    }
}