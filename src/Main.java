package src;

import java.util.Random;

public class Main {

    private static final int MAX_COST = 20;
    private static final String SOURCE_NODE = "A";
    private static final String DEST_NODE = "I";
   // private static final String[] DESTS = {"I","H"};


    public static void main(String[] args) {

        Network virtualNet = new Network();
        setRandomCosts(virtualNet);
        UGraph graph = new UGraph(virtualNet);
        Dijkstra dijkstra = new Dijkstra();



        /**
        *
        *  It makes good sense to have the labels reprsent the exact
        *   and necessary path from the current node to the destination
        *   node... the label will be an array of strings where for each
        *   string...
        *
        *   char [0]   = incoming port/interface id
        *   char [1]   = its own id
        *   char [2]   = outgoing port/interface id
        *   char [...] = next outgoing interface id
        *   char [n]   = destination id
        *
        *   multicasting occurs naturaly as each node runs the dijskstra to
        *    generate its outgoing label, based on the incoming label...
        *
        *     @TODO: recurse through set of nodes with multiple destinations developing needed labels along the way
        *
        */
        char [] nextPath = dijkstra.runDijk(graph,SOURCE_NODE,DEST_NODE,false);
        System.out.printf("\n    Label from Source is -> %s\n", String.valueOf(nextPath));

    }

    private static void setRandomCosts(Network vn){

        int size = vn.getNet().length;
        int nextCost;
        Random ran = new Random();
        for (int i = 0; i < size; i++) {
            nextCost = ran.nextInt((MAX_COST) -1 )+1;
            vn.getNet()[i].setDist(nextCost);
        }
    }

}