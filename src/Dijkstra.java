package src;

import java.util.*;

/**
 * Created by NateMarder on 4/29/15.
 */
public class Dijkstra {

    protected ArrayList<String> bestPath = new ArrayList<>();
    protected String source;
    protected String dest;

    public char[] runDijk(UGraph theGraph, String startName, String destName, Boolean quiet) {
        this.source = startName;
        this.dest = destName;

        Vertex u, v;

        HashMap<String, Vertex> graph = theGraph.getGraph();

        if (!graph.containsKey(startName)) {
            System.err.printf("UGraph doesn't contain start vertex \"%s\"\n", startName);
            return null;
        }

        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> treeSet = new TreeSet<>();

        for (Vertex vertex : graph.values()) {
            if (vertex == source) {
                vertex.prev = source;
                vertex.dist = 0;
            } else {
                vertex.prev = null;
                vertex.dist = Integer.MAX_VALUE;
            }
            treeSet.add(vertex);
        }

        while (!treeSet.isEmpty()) {

            u = treeSet.pollFirst(); //pollFirst retrieves/removes first item on treeSet

            if (u.dist == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> neighborVertex : u.neighbors.entrySet()) {
                v = neighborVertex.getKey(); //the neighbour in this iteration

                final int alternateDist = u.dist + neighborVertex.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    treeSet.remove(v);
                    v.dist = alternateDist;
                    v.prev = u;
                    treeSet.add(v);
                }
            }
        }

        printAllPaths(theGraph, destName, quiet);
        return getLabel();
    }


    /**
     * Prints the path from the source to every vertex
     */
    public void printAllPaths(UGraph theGraph, String dest, Boolean quiet) {
        HashMap<String, Vertex> graph = theGraph.getGraph();
        if (!quiet){
            for (Vertex v : graph.values()) {
                v.printPath();
                System.out.println();
            }
        }
        printPath(theGraph,dest);
    }

    /**
     * Prints a path from the source to the specified vertex
     */
    public void printPath(UGraph theGraph, String destVertex) {

        HashMap<String, Vertex> graph = theGraph.getGraph();
        if (!graph.containsKey(destVertex)) {
            System.err.printf("UGraph doesn't contain end vertex \"%s\"\n", destVertex);
            return;
        }
        getPath(graph.get(destVertex)); // get the best path
    }


    protected void getPath(Vertex vertex) {
        this.bestPath.add(0, vertex.id);
        if (vertex == vertex.prev) { }
        else if (vertex.prev == null) {
            System.out.print(vertex.id + "(unreachable)");
        } else {
            getPath(vertex.prev);
        }
    }


    public char[] getLabel(){

        char[]thePath = new char[this.bestPath.size()];
        for (int i = 0 ; i<this.bestPath.size(); i++){
            thePath[i] = this.bestPath.get(i).toCharArray()[0];
        }

        return thePath;


    }
}
