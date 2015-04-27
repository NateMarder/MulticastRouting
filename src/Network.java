package src;

/**
 * Represents the network illustrated in the groups project handout.
 * The syntax to create a new edge is ("vertex-1", "vertex-2", int cost).
 */
public class Network {

    private int vSize = 14; //number of vertexes

    private Edge[] network = {
            new Edge("A", "B", 6),
            new Edge("B", "D", 2),
            new Edge("B", "K", 18),
            new Edge("C", "F", 8),
            new Edge("D", "E", 1),
            new Edge("E", "F", 4),
            new Edge("E", "G", 2),
            new Edge("F", "G", 7),
            new Edge("F", "N", 18),
            new Edge("G", "H", 1),
            new Edge("H", "I", 3),
            new Edge("I", "G", 7),
            new Edge("I", "L", 3),
            new Edge("I", "M", 3),
            new Edge("A", "C", 7),
            new Edge("K", "L", 4),
            new Edge("K", "M", 5),
            new Edge("L", "N", 5),
            new Edge("M", "N", 1),
    };

    protected Edge[] getNet() {
        return network;
    }

    protected int getvSize(){
        return vSize;
    }


    /**
     * The Edge class contains string representation of nodes connected by the Edge.
     * The field dist represents the cost which is used to compute the shortest
     * path algorithm.
     */
    protected class Edge {
        private String v1;
        private String v2;
        private int dist;

        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }

        protected void setDist(int dist) {
            this.dist = dist;
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
