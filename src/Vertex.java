package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Vertex class implements Comparable<Vertex>. The dist field is used for Comparison
 * The constructor only requires a String id. The UGraph containing the vertex
 * must ensure the Vertex's id is unique.
 */
public class Vertex implements Comparable<Vertex> {

    /** Name of this vertex */
    protected String id;

    /** weight */
    protected int dist = Integer.MAX_VALUE;

    /** link to the previous vertex */
    protected Vertex prev = null;

    protected final Map<Vertex, Integer> neighbors = new HashMap<>();

    /** Constructor requires vertex id */
    public Vertex(String id) {
        this.id = id;
    }

    // Return an array representing the path from src to dest
    public void pathList(ArrayList<String> pl) {
        if (this == this.prev) {
            pl.add(this.id);
        } else if (this.prev != null) {
            this.prev.pathList(pl);
            pl.add(this.id);
        }
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(dist, other.dist);
    }

    public void multiCastDemo(ArrayList<String> theLabel, UGraph uGraph, String pt){

        ArrayList<String> outGoingLabels = new ArrayList<>();
        for (int i = 0; i<theLabel.size(); i++){

            // destination reached
            if (theLabel.get(i).length()==1){
                System.out.println("destination reached");

            // destination not reached so adjust label for recursive call
            } else{
                String outGoing = theLabel.get(i).substring(1);
                outGoingLabels.add(outGoing);
            }
        }

        System.out.println("\n\nMy Name: " + this.id);
        System.out.println("Incoming Label from port "+ pt + ": "+theLabel.toString());
        System.out.println("           Outgoing Labels: "+outGoingLabels.toString());

        if (outGoingLabels.size()>=1){
            for (int i = 0; i<outGoingLabels.size(); i++){

                Vertex next = uGraph.graph.get(String.valueOf(outGoingLabels.get(i).charAt(0)));
                String test = String.valueOf(outGoingLabels.get(i).charAt(0));
                String nextOutLabel = outGoingLabels.get(i);
                ArrayList<String> subBatch = new ArrayList<>();

                while (test.matches(String.valueOf(nextOutLabel.charAt(0)))) {
                    subBatch.add(outGoingLabels.get(i++));

                    if ( i < outGoingLabels.size() ){
                        nextOutLabel = outGoingLabels.get(i);
                    }else {
                        i = i-1;
                        break;  }
                }

                String port = next.id;
                System.out.println(this.id+" is sending out sub-batch: "+subBatch.toString()+" to "+port);

                // recursive casting...
                next.multiCastDemo(subBatch, uGraph, this.id);

            }
        }





    }
}