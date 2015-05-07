package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    /** Tests multicast simulation by creating a network of vertices and displaying
     * an MPLS routing table from each node to every other node.
     *
     * @see src.Network
     * @see src.UGraph
     * @see src.Vertex
     * @param args
     */

    private static final String LABELS_DIRECTORY_PATH = makeDirectoryForTables("/VertexTables/");

    public static void main(String[] args) {
        Network network = new Network();
        UGraph graph = new UGraph(network);

        for (String src : graph.getNodesNames()) {

            ArrayList<String>theLabel = new ArrayList<>();

            // Run shortest path first to set up the distances and paths
            graph.findShortestPaths(src);

            // Table header
            System.out.printf("---BEGIN TABLE FOR NODE %s---\n", src);
            theLabel.add("\n\n      ****** LABEL TABLE FOR NODE " + src + " ******\n\n");
            theLabel.add("\n  Dest   OUTGOING_PATH   OUT-PORT      SENT-LABEL\n");

            // Sets a source and makes an MPLS table entry to every other vertex
            for (String dest : graph.getNodesNames()) {
                graph.makeTableEntry(src, dest);  // sout
                theLabel.add(graph.makeTableEntry2(src, dest)); // to file
            }

            writeToFile(theLabel,("/"+src+".txt"));

            // Spaces between tables looks nice
            System.out.printf("\n\n\n");
        }

    }

    private static void writeToFile(ArrayList<String> labelData, String fileName) {
        String content = "";
        String pathAndName = LABELS_DIRECTORY_PATH + fileName;
        try (FileWriter fileWriter = new FileWriter(pathAndName)) {
            for (String nextLine: labelData){
                fileWriter.write(nextLine);
            }
            fileWriter.write(content);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     * This method makes a directory to store the vertex label tables.
     * The output is stored safely within the compiler's output directory.
     */
    public static String makeDirectoryForTables(String directoryName){
        final File f = new File(src.Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File directory = new File(f.getPath()+directoryName);
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