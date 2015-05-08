package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Vertex class implements Comparable<Vertex>. The dist field is used for Comparison
 * The constructor only requires a String id. The UGraph containing the vertex
 * must ensure the Vertex's id is unique.
 */
public class Vertex implements Comparable<Vertex> {

    /**
     * Name of this vertex
     */
    protected String id;

    /**
     * weight
     */
    protected int dist = Integer.MAX_VALUE;

    /**
     * link to the previous vertex
     */
    protected Vertex prev = null;

    protected final Map<Vertex, Integer> neighbors = new HashMap<>();
    protected static final String LABELS_DIRECTORY_PATH = makeDirectoryForTables("/VertexTables/");

    /**
     * Constructor requires vertex id
     */
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

    /**
     * This is for processing and creating labels dynamically
     */
    public void labelPass(ArrayList<String> label_IN, UGraph graph, boolean isSource) {

        ArrayList<String> outPorts = new ArrayList<>();
        ArrayList<String> inPorts = new ArrayList<>();
        ArrayList<String> destRec = new ArrayList<>();
        HashMap<String, ArrayList<String>> labelsOUT = new HashMap<>();  // key = out-port, value = ArrayList<String>
        HashMap<String, ArrayList<String>> labelsIN = new HashMap<>();  // key = out-port, value = ArrayList<String>
        ArrayList<String> printThis = new ArrayList<>();

        System.out.println("");

        for (int i = 0; i < label_IN.size(); i++) {

            String nextLabel = label_IN.get(i);

            String nextLabelOUT;

            if (!isSource) {
                nextLabelOUT = nextLabel.substring(1);
            } else {
                nextLabelOUT = nextLabel;
            }

            String nextPortIN = nextLabel.substring(0, 1);  // get first letter which is incoming port

            if (label_IN.get(i).length() > 2) {

                String nextPortOUT;
                if (!isSource) {
                    nextPortOUT = nextLabel.substring(2, 3);  // get third letter which outgoing port
                } else {
                    nextPortOUT = nextLabel.substring(1, 2);  // get third letter which outgoing port
                }

                if (!outPorts.contains(nextPortOUT)) {
                    outPorts.add(nextPortOUT);
                    labelsOUT.put(nextPortOUT, new ArrayList<String>());
                }
                labelsOUT.get(nextPortOUT).add(nextLabelOUT);

                if (!inPorts.contains(nextPortIN) && !isSource) {
                    inPorts.add(nextPortIN);
                    labelsIN.put(nextPortIN, new ArrayList<String>());
                }
                if (!isSource) {
                    labelsIN.get(nextPortIN).add(nextLabel);
                }
            } else {
                destRec.add(" -->Received Data From " + nextPortIN);
            }
        }
        printThis.add("This Node: " + this.id + "\n");
        for (String nextKey : labelsIN.keySet()) {
            String nextLine = "From Port " + nextKey + " label:" + labelsIN.get(nextKey).toString();
            printThis.add(nextLine + "\n");
        }

        for (String nextKey : labelsOUT.keySet()) {
            String nextLine = "  TO Port " + nextKey + " label:" + labelsOUT.get(nextKey).toString();
            printThis.add(nextLine + "\n");
            graph.graph.get(nextKey).labelPass(labelsOUT.get(nextKey), graph, false);
        }

        for (String dataReceived : destRec) {
            printThis.add(dataReceived + "\n");
        }

        writeToFile(printThis, this.id + ".txt");


    }

    private static void writeToFile(ArrayList<String> labelData, String fileName) {
        String pathAndName = LABELS_DIRECTORY_PATH + "/justPassed--" + fileName;
        try (FileWriter fileWriter = new FileWriter(pathAndName)) {
            for (String nextLine : labelData) {
                fileWriter.write(nextLine); // to file
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static String makeDirectoryForTables(String directoryName) {
        final File f = new File(src.Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File directory = new File(f.getPath() + directoryName);
        if (directory.exists()) {
            return directory.getPath();
        } else {
            System.out.println("Directory not exists, creating now");
            boolean success = directory.mkdir();
            if (success) {
                System.out.printf("Successfully created new directory : %s%n", directoryName);
                return directory.getPath();
            } else {
                System.out.printf("Failed to create new directory: %s%n", directoryName);
                return "failed";
            }
        }

    }
}





